package com.github.chenzm77.goods.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long spuId;
    private String spuName;
    private String description;
    private String mainImage;
    private List<String> images;
    private List<String> tags;
    private Long sales;
    private Integer status;
    private List<SkuDTO> skus;
    private ActivityDTO activity;
}
