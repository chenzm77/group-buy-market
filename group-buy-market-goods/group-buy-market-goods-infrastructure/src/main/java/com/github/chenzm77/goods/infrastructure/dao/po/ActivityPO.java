package com.github.chenzm77.goods.infrastructure.dao.po;

import lombok.Data;

@Data
public class ActivityPO {
    private Long id;
    private Long spuId;
    private String name;
    private Long startTime;
    private Long endTime;
    private Integer groupSize;
    private Integer status;
    private Long createTime;
}
