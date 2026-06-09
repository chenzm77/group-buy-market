package com.github.chenzm77.goods.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long spuId;
    private String spuName;
    private String mainImage;
    private Long minPrice;
    private Long activityMinPrice;
    private Long sortPrice;
    private Long sales;
    private Integer activityStatus;
}
