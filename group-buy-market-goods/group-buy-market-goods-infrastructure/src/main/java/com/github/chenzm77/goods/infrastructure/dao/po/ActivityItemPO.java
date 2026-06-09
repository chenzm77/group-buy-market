package com.github.chenzm77.goods.infrastructure.dao.po;

import lombok.Data;

@Data
public class ActivityItemPO {
    private Long id;
    private Long activityId;
    private Long skuId;
    private Long seckillPrice;
    private Integer perUserLimit;
    private Long createTime;
}
