package com.github.chenzm77.goods.domain.product.model.vo;

import com.github.chenzm77.goods.types.enums.ResponseCode;
import com.github.chenzm77.goods.types.exception.AppException;
import lombok.Value;

@Value
public class Money {
    Long value;

    public Money(Long value) {
        if (value == null || value < 0) {
            throw new AppException(ResponseCode.PRICE_INVALID.getCode(), ResponseCode.PRICE_INVALID.getInfo());
        }
        this.value = value;
    }
}
