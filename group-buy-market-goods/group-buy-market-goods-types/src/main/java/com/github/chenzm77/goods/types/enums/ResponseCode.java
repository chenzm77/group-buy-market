package com.github.chenzm77.goods.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS("0000", "成功"),
    SYSTEM_ERROR("0001", "系统异常"),
    INVALID_PARAMETER("0002", "参数非法"),
    SPU_NOT_FOUND("1001", "商品不存在"),
    SKU_NOT_FOUND("1002", "SKU不存在"),
    ACTIVITY_NOT_FOUND("2001", "活动不存在"),
    ACTIVITY_ITEM_NOT_FOUND("2002", "活动商品不存在"),
    PRODUCT_NOT_SALEABLE("3001", "商品不可售"),
    PRICE_INVALID("3002", "价格非法");

    private final String code;
    private final String info;
}
