package com.github.chenzm77.goods.domain.event;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public abstract class DomainEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String eventId;
    private final Long occurredAt;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.occurredAt = Instant.now().getEpochSecond();
    }
}
