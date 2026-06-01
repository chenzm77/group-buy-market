package com.github.chenzm77.infrastructure.adapter.repository;

import com.github.chenzm77.domain.user.adapter.repository.IUserRepository;
import com.github.chenzm77.domain.user.model.entity.UserEntity;
import com.github.chenzm77.infrastructure.dao.IUserDao;
import com.github.chenzm77.infrastructure.dao.po.User;
import com.github.chenzm77.infrastructure.util.SnowflakeIdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository implements IUserRepository {

    @Resource
    private IUserDao userDao;

    @Resource
    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public UserEntity queryUserById(Long userId) {
        User user = userDao.selectById(userId);
        return toEntity(user);
    }

    @Override
    public UserEntity queryUserByPhone(String phone) {
        User user = userDao.selectByPhone(phone);
        return toEntity(user);
    }

    @Override
    public boolean saveUser(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            userEntity.setId(snowflakeIdGenerator.nextId());
        }
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return userDao.insert(user) > 0;
    }

    @Override
    public boolean updateUser(UserEntity userEntity) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return userDao.updateById(user) > 0;
    }

    @Override
    public List<UserEntity> batchQueryUsers(List<Long> userIds) {
        List<User> users = userDao.selectByIds(userIds);
        return users.stream().map(this::toEntity).collect(Collectors.toList());
    }

    private UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(user, entity);
        return entity;
    }
}