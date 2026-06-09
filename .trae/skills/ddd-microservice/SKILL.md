---
name: "ddd-microservice"
description: "Generate DDD microservice project structure based on standardized architecture. Invoke when user wants to create a new microservice, scaffold DDD project, or set up microservice architecture."
---

# DDD 微服务项目生成器

本 Skill 用于根据标准化的 DDD 架构规范生成微服务项目框架。

## 使用场景

- 用户想要创建新的微服务项目
- 用户需要搭建 DDD 架构的项目骨架
- 用户提到"新建服务"、"搭建项目"、"生成微服务"等

## 架构规范

### 一、概述

本规范定义了一套极简、可落地的 DDD 微服务架构标准。核心原则：

1. **所有业务逻辑必须在领域层**，不允许向上（应用层）或向下（基础设施层）溢出。
2. **应用层只做用例编排**，不包含任何业务规则（哪怕一个 if 判断业务条件都不行）。
3. **基础设施层只做技术适配**，实现领域层定义的接口（仓储、防腐层等）。
4. **包结构平铺直叙**，不引入多余的抽象层（如 port、adapter 在领域层中禁止出现）。

---

### 二、项目模块划分（Maven 多模块）

```
{project-name}/
├── {project-name}-api            # 接口契约层：RPC 接口、DTO、响应封装
├── {project-name}-app            # 应用层：用例编排、事务管理、Spring Boot 启动类
├── {project-name}-domain         # 领域层：聚合根、实体、值对象、仓储接口、防腐层接口、领域服务
├── {project-name}-infrastructure # 基础设施层：仓储实现、防腐层实现、DAO、PO、Redis、工具类
├── {project-name}-trigger        # 触发器层：RPC 实现、HTTP Controller、MQ 消费者
└── {project-name}-types          # 共享类型层：常量、枚举、异常基类、事件基类
```

#### 编译时依赖原则

- `domain` 不依赖任何业务模块（只依赖 `types`）
- `infrastructure` 依赖 `domain`，实现其接口
- `app` 依赖 `domain`，可依赖 `api`（类型转换）
- `trigger` 依赖 `api` 和 `app`
- `api` 只依赖 `types`

---

### 三、各模块详细规范

#### 3.1 types — 共享类型层

**用途**：存放全项目通用的基础类型，无任何业务逻辑。

##### 包结构示例

```
com.xxx.types/
├── common/Constants.java
├── enums/ResponseCode.java
├── event/BaseEvent.java
└── exception/AppException.java
```

##### 编码要点

- `Constants` 用嵌套静态内部类分组。
- `ResponseCode` 枚举包含 `code` 和 `info`。
- `BaseEvent<T>` 泛型事件基类，自动设置 `eventTime`。
- `AppException` 继承 `RuntimeException`，包含 `code`。

##### pom.xml

仅依赖 Lombok、commons-lang3 等通用库，不依赖任何业务模块。

---

#### 3.2 api — 接口契约层

**用途**：定义对外暴露的 RPC 接口、请求/响应 DTO、通用响应体。可被外部消费者独立引用。

##### 包结构示例

```
com.xxx.api/
├── dto/XxxRequestDTO.java
├── dto/XxxResponseDTO.java
├── response/Response.java
└── IXxxService.java
```

##### 编码要点

- DTO 使用 `@Data`、`@Builder`、`@NoArgsConstructor`、`@AllArgsConstructor`，实现 `Serializable`。
- `Response<T>` 包含 `code`、`msg`、`data`，提供静态工厂方法 `success()` 和 `fail()`。
- RPC 接口方法返回 `Response<T>` 或直接 DTO。

##### pom.xml

仅依赖 Lombok 和 Jakarta Validation，不依赖内部业务模块。

---

#### 3.3 domain — 领域层（核心）

**用途**：所有业务逻辑的唯一驻地。包含聚合根、实体、值对象、仓储接口、防腐层接口、领域服务。

##### 核心约束

- ❌ 不允许依赖 `infrastructure`、`app`、`trigger`、`api`
- ✅ 只依赖 `types` 和通用工具库（fastjson、guava、commons-lang3）
- ✅ 可以依赖 Spring 的 `@Service` 注解（为了 IoC），但不依赖其他 Spring 技术（如 `@Transactional`）

##### 3.3.1 包结构（按聚合根分包）

一个限界上下文（微服务）内可能有多个聚合根，每个聚合根独立一个包：

```
com.xxx.domain/
├── user/                     # 用户聚合根包
│   ├── model/
│   │   ├── User.java         # 聚合根
│   │   ├── Address.java      # 子实体
│   │   └── valueobjects/     # 值对象
│   ├── repository/
│   │   └── UserRepository.java   # 仓储接口
│   ├── acl/
│   │   └── UserInfoProvider.java # 防腐层接口（读外部用户信息）
│   └── service/
│       ├── UserDomainService.java       # 领域服务接口
│       └── UserDomainServiceImpl.java   # 领域服务实现
├── order/                    # 订单聚合根包（同一服务内另一个聚合根）
│   ├── model/
│   ├── repository/
│   ├── acl/
│   └── service/
└── ...
```

> **说明**：没有 adapter、port 等多余包名，直接 repository、acl、service、model 平铺。每个聚合根包独立，互不干扰。

##### 3.3.2 实体（Entity / 聚合根）

使用 Lombok 注解，业务规则方法直接定义在实体内部，不依赖任何框架注解。

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Integer status;

    public void upgradeToVip() {
        if (this.status == Status.VIP) {
            throw new AppException("已经是VIP用户");
        }
        this.status = Status.VIP;
        // 其他业务规则...
    }
}
```

##### 3.3.3 仓储接口（Repository）

定义在 `repository/` 包下，只操作领域实体，方法命名：`save`、`findById`、`update`、`delete`。

```java
public interface UserRepository {
    User findById(Long id);
    void save(User user);
    void update(User user);
    void delete(Long id);
}
```

##### 3.3.4 防腐层接口（ACL）

定义在 `acl/` 包下，用于隔离外部服务；出入参使用领域模型，不暴露外部 DTO。

```java
public interface UserInfoProvider {
    UserProfile getUserProfile(Long userId);  // UserProfile 是领域值对象
}
```

##### 3.3.5 领域服务（Domain Service）

接口与实现分离，实现类标注 `@Service`；负责加载聚合、执行业务、落库、发布事件，不处理事务。

```java
// 接口
public interface UserDomainService {
    void upgradeToVip(Long userId);
}

// 实现
@Slf4j
@Service
public class UserDomainServiceImpl implements UserDomainService {
    @Resource private UserRepository userRepository;
    @Resource private DomainEventPublisher eventPublisher; // 事件发布接口

    @Override
    public void upgradeToVip(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) throw new AppException("用户不存在");
        user.upgradeToVip();               // 聚合根内的业务规则
        userRepository.save(user);          // 持久化
        eventPublisher.publish(new UserUpgradedEvent(userId));
    }
}
```

> **原理**：仓储接口归属领域层、实现在 infrastructure，依赖倒置，领域不依赖基础设施。

---

#### 3.4 infrastructure — 基础设施层

**用途**：实现 domain 层定义的仓储接口和防腐层接口，处理所有技术细节（数据库、缓存、RPC 调用、消息等）。

##### 包结构

```
com.xxx.infrastructure/
├── dao/                         # 数据访问
│   ├── po/                      # 持久化对象
│   │   └── UserPO.java
│   └── UserMapper.java          # MyBatis Mapper
├── adapter/                     # 适配器（实现 domain 层接口）
│   ├── repository/              # 仓储实现
│   │   └── UserRepositoryImpl.java
│   └── acl/                     # 防腐层实现
│       └── UserInfoProviderImpl.java
├── redis/
│   └── RedisService.java        # Redis 封装
└── util/
    └── SnowflakeIdGenerator.java
```

##### 编码规范

- **PO**：和数据库表一一对应，`@Data`；**Mapper**：`@Mapper`
- **仓储实现**：`@Repository`，实现领域仓储接口，负责 PO↔Entity 转换，无业务逻辑
- **防腐实现**：`@Component`/`@Service`，调用外部 RPC/HTTP，外部 DTO→领域对象

```java
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Resource private UserMapper userMapper;
    @Resource private SnowflakeIdGenerator idGenerator;

    @Override
    public User findById(Long id) {
        UserPO po = userMapper.selectById(id);
        return toEntity(po);
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.nextId());
        }
        UserPO po = toPO(user);
        userMapper.insert(po);
    }

    private User toEntity(UserPO po) { ... }
    private UserPO toPO(User user) { ... }
}
```

##### pom.xml

依赖 `domain`、MyBatis、Redisson、Dubbo（用于调用外部服务）等。

---

#### 3.5 app — 应用层

**用途**：用例编排与事务管理，不包含任何业务规则。是 Spring Boot 启动模块。

##### 包结构

```
com.xxx.app/
├── service/
│   └── UserApplicationService.java
├── config/
│   └── AppConfig.java
└── Application.java              # @SpringBootApplication
```

##### 编码规范

- 应用服务 `@Service`，只注入领域服务，**禁止直连仓储/外部 RPC**
- 方法职责：编排领域调用 + `@Transactional` 管控事务、日志、权限，**禁止业务 if 判断**

```java
@Service
public class UserApplicationService {
    @Resource private UserDomainService userDomainService;
    @Resource private OrderDomainService orderDomainService; // 另一个聚合根的领域服务

    @Transactional
    public void upgradeUserToVip(Long userId) {
        // 调用领域服务完成用户升级（包含加载、校验、保存）
        userDomainService.upgradeToVip(userId);
        // 如果还需要协同其他领域服务，继续调用（编排）
        orderDomainService.createVipOrder(userId);
    }
}
```

```java
@SpringBootApplication
@EnableDubbo
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

##### pom.xml

依赖 `domain`、`api`、Spring Boot Starter、Dubbo、Redisson。

---

#### 3.6 trigger — 触发器层

**用途**：接收外部请求（RPC、HTTP、MQ），转换为领域调用，极薄。

##### 包结构

```
com.xxx.trigger/
├── rpc/
│   └── UserServiceRpc.java        # 实现 api 中的接口
├── http/
│   └── UserController.java        # 可选
└── mq/
    └── OrderConsumer.java         # 可选
```

##### 编码规范

- RPC 实现 `@DubboService`；只做：**参数转换→调用 app→结果封装**，无任何业务逻辑
- **禁止直连 domain/infrastructure**

```java
@DubboService(version = "1.0.0")
public class UserServiceRpc implements IUserService {
    @Resource private UserApplicationService userApplicationService;

    @Override
    public Response<Void> upgradeToVip(Long userId) {
        userApplicationService.upgradeUserToVip(userId);
        return Response.success(null);
    }
}
```

##### pom.xml

依赖 `api`、`app`、`infrastructure`（启动时汇集），以及 Dubbo、Spring Boot Web。

---

### 四、完整调用链路示例（从 RPC 到数据库）

```
外部 Dubbo 请求
  → trigger/rpc/UserServiceRpc.upgradeToVip()
    → app/UserApplicationService.upgradeUserToVip()  [@Transactional]
      → domain/user/service/UserDomainServiceImpl.upgradeToVip()
        → 调用 domain/user/repository/UserRepository.findById()
          → infrastructure/adapter/repository/UserRepositoryImpl.findById()
            → infrastructure/dao/UserMapper.selectById()
              → 数据库
        → 调用聚合根 user.upgradeToVip()（业务规则）
        → 调用 UserRepository.save()
        → 发布领域事件（可选）
      ← 返回
    ← 返回
  ← 返回 Response
```

> **关键点**：业务逻辑全部在 domain 层，app 只做编排和事务，trigger 只做协议转换。

---

### 五、编译时依赖关系图

```
trigger  ──→ app  ──→ domain  ←── infrastructure
  │          │         │              │
  └──────────┴──── types ←────────────┘
  │                                    │
  └──────────── api ←──────────────────┘
```

#### 依赖约束表

| 模块 | 允许依赖 | 禁止依赖 |
|------|----------|----------|
| types | 无 | 任何业务模块 |
| api | types | domain, app, infrastructure, trigger |
| domain | types | api, app, infrastructure, trigger |
| infrastructure | domain, api（外部调用） | app, trigger |
| app | domain, api（类型转换） | infrastructure, trigger |
| trigger | api, app, infrastructure | domain 直接 |

---

### 六、配置规范（简要）

1. **主配置 application.yml**：仅 `spring.application.name`、`server.port`、`spring.profiles.active`。
2. **环境配置 application-{profile}.yml**：数据源、Redis、Dubbo、MyBatis 等。
3. **MyBatis 配置**：`mybatis-config.xml` 开启驼峰映射。
4. **日志**：`logback-spring.xml`。

---

### 七、核心原则总结（口诀）

> **domain 写规则，repo+acl 定义接口；**
> **infra 实现接口，只做技术活；**
> **app 编排调领域，事务切面放这里；**
> **trigger 薄薄转一层，绝对不写业务。**

---

### 八、禁止事项清单

| 禁止行为 | 后果 |
|----------|------|
| 在 app 层写 if 判断业务条件 | 业务逻辑泄漏，难以测试 |
| 在 app 层直接调用 repository | 打破领域封装，事务混乱 |
| 在 infrastructure 的仓储实现中写业务规则 | 技术细节污染业务 |
| 在 domain 层引入 @Transactional | 破坏纯净性，事务应属应用层 |
| 在 trigger 层调用 domain 或 infrastructure | 越界，应通过 app 层 |
| 领域层中出现 adapter、port 等无意义包名 | 增加认知负担，不需要 |

---

## 执行步骤

当用户请求创建新的微服务时，按以下步骤执行：

1. **确认服务名称**：询问用户微服务名称（如 `order`、`product`、`payment` 等）
2. **确认聚合根**：询问用户该服务包含哪些聚合根
3. **生成项目结构**：按照上述规范创建 Maven 多模块项目
4. **生成基础代码**：
   - types 层：Constants、ResponseCode、AppException、BaseEvent
   - api 层：接口定义、DTO、Response
   - domain 层：实体、仓储接口、领域服务接口与实现
   - infrastructure 层：仓储实现、DAO、PO
   - app 层：ApplicationService、Application 启动类
   - trigger 层：RPC 实现
5. **生成配置文件**：pom.xml、application.yml、mybatis-config.xml、logback-spring.xml
6. **验证依赖关系**：确保各模块依赖关系符合规范
