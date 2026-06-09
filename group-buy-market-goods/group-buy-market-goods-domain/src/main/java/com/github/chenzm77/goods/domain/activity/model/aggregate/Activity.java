package com.github.chenzm77.goods.domain.activity.model.aggregate;

import com.github.chenzm77.goods.domain.activity.model.entity.ActivityItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long spuId;
    private String name;
    private Long startTime;
    private Long endTime;
    private Integer groupSize;
    private Integer status;
    private Long createTime;
    private List<ActivityItem> items;

    public void create() {
        this.createTime = Instant.now().getEpochSecond();
        this.status = status == null ? 0 : status;
    }

    public void enable() {
        this.status = 1;
    }

    public void disable() {
        this.status = 0;
    }

    public boolean isEffectiveNow() {
        long now = Instant.now().getEpochSecond();
        return status != null && status == 1
                && startTime != null && startTime <= now
                && endTime != null && endTime >= now;
    }
}
