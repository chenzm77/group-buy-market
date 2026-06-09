package com.github.chenzm77.goods.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductQueryType {
    HOME("HOME", "首页展示"),
    KEYWORD_DEFAULT("KEYWORD_DEFAULT", "关键词默认相关性"),
    SALES_DESC("SALES_DESC", "销量从高到低"),
    PRICE_DESC("PRICE_DESC", "价格降序"),
    PRICE_ASC("PRICE_ASC", "价格升序"),
    ACTIVITY_ONLY("ACTIVITY_ONLY", "仅活动");

    private final String code;
    private final String info;

    public static ProductQueryType of(String code) {
        for (ProductQueryType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return KEYWORD_DEFAULT;
    }
}
