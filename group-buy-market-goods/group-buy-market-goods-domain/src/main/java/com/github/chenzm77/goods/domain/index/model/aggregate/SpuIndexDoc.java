package com.github.chenzm77.goods.domain.index.model.aggregate;

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
public class SpuIndexDoc implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long spuId;
    private String spuName;
    private List<String> tags;
    private String mainImage;
    private Long minPrice;
    private Long activityMinPrice;
    private Long sortPrice;
    private Long sales;
    private Integer activityStatus;
    private Long createTime;
}
