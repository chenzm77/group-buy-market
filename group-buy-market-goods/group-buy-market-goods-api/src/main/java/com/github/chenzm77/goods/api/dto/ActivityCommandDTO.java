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
public class ActivityCommandDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long activityId;
    private Long spuId;
    private String name;
    private Long startTime;
    private Long endTime;
    private Integer groupSize;
    private Integer status;
    private List<ActivityItemCommandDTO> items;
}
