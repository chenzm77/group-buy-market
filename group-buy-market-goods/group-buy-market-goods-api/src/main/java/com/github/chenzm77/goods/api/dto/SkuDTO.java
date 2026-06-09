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
public class SkuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long skuId;
    private String specJson;
    private Long price;
    private String image;
    private Integer status;
}
