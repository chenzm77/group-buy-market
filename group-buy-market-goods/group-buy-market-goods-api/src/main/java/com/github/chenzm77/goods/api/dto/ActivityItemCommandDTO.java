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
public class ActivityItemCommandDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long activityItemId;
    private Long activityId;
    private Long spuId;
    private Long skuId;

    private Long seckillPrice;
    private Integer perUserLimit;
}
