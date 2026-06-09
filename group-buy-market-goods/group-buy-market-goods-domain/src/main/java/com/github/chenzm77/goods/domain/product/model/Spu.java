package com.github.chenzm77.goods.domain.product.model;

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
public class Spu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private String mainImage;
    private List<String> images;
    private List<String> tags;
    private Long sales;
    private Integer status;
    private Long createTime;
    private Long updateTime;
    private List<Sku> skus;

    public void publish() {
        this.status = 1;
    }

    public void unpublish() {
        this.status = 2;
    }

    public void updateSales(Long sales) {
        this.sales = sales == null ? 0L : sales;
    }

    public boolean isSearchable() {
        return status != null && status == 1;
    }
}
