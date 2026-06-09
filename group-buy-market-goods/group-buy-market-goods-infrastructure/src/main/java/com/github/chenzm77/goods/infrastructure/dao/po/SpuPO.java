package com.github.chenzm77.goods.infrastructure.dao.po;

import lombok.Data;

@Data
public class SpuPO {
    private Long id;
    private String name;
    private String description;
    private String mainImage;
    private String images;
    private String tags;
    private Long sales;
    private Integer status;
    private Long createTime;
    private Long updateTime;
}
