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
public class ProductSearchRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String keyword;
    private String sort;
    private Integer page;
    private Boolean activityOnly;
}