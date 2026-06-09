package com.github.chenzm77.goods.domain.event;

public interface DomainEventPublisher {

    void publish(DomainEvent event);
}
