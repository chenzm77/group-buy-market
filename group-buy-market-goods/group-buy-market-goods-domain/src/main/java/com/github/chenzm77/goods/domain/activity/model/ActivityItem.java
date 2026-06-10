package com.github.chenzm77.goods.domain.activity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long activityId;
    private Long skuId;
    private Long seckillPrice;
    private Integer perUserLimit;
    private Long createTime;

    public void changePrice(Long seckillPrice) {
        this.seckillPrice = seckillPrice;
    }
}
