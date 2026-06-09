package com.github.chenzm77.goods.infrastructure.adapter.mq;

import com.github.chenzm77.goods.domain.event.DomainEvent;
import com.github.chenzm77.goods.domain.event.DomainEventPublisher;
import com.github.chenzm77.goods.domain.event.SpuIndexRebuildEvent;
import com.github.chenzm77.goods.types.common.Constants;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RocketMqDomainEventPublisher implements DomainEventPublisher {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void publish(DomainEvent event) {
        rocketMQTemplate.convertAndSend(topicMapping(event), event);
    }

    private String topicMapping(DomainEvent event) {
        if (event instanceof SpuIndexRebuildEvent) {
            return Constants.MqTopic.SPU_INDEX_REBUILD;
        }
        throw new IllegalArgumentException("未配置领域事件Topic映射: " + event.getClass().getName());
    }
}
