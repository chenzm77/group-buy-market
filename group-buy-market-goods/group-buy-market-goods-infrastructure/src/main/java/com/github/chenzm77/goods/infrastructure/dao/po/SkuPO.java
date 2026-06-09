package com.github.chenzm77.goods.infrastructure.dao.po;

import lombok.Data;

@Data
public class SkuPO {
    private Long id;
    private Long spuId;
    private String specJson;
    private Long price;
    private String image;
    private Integer status;
    private Long createTime;
    private Long updateTime;
}
