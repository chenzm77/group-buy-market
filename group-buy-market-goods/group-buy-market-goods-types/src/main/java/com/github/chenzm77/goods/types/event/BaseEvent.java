package com.github.chenzm77.goods.types.event;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;

@Getter
public class BaseEvent<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String eventId;
    private final Long eventTime;
    private final T data;

    public BaseEvent(String eventId, T data) {
        this.eventId = eventId;
        this.data = data;
        this.eventTime = Instant.now().getEpochSecond();
    }
}
