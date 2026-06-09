package com.github.chenzm77.goods.domain.event;

import lombok.Getter;

@Getter
public class SpuIndexRebuildEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    private final Long spuId;
    private final String reason;

    public SpuIndexRebuildEvent(Long spuId, String reason) {
        super();
        this.spuId = spuId;
        this.reason = reason;
    }
}
