package com.github.chenzm77.types.event;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class BaseEvent<T> {

    private String eventId;
    private Date eventTime;
    private T data;

    public BaseEvent() {
        this.eventTime = new Date();
    }

    public BaseEvent(T data) {
        this.eventTime = new Date();
        this.data = data;
    }
}