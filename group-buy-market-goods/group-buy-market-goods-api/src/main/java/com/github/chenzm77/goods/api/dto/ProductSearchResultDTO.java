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
public class ProductSearchResultDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ProductItemDTO> items;
    private Integer total;
    private Integer totalPages;
    private Integer currentPage;
}
