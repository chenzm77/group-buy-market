package com.github.chenzm77.goods.domain.product.model.aggregate;

import com.github.chenzm77.goods.domain.product.model.entity.Sku;
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

    public void create() {
        long now = Instant.now().getEpochSecond();
        this.createTime = now;
        this.updateTime = now;
        this.sales = sales == null ? 0L : sales;
        this.status = status == null ? 0 : status;
    }

    public void touch() {
        this.updateTime = Instant.now().getEpochSecond();
    }

    public void publish() {
        this.status = 1;
        touch();
    }

    public void unpublish() {
        this.status = 2;
        touch();
    }

    public void updateSales(Long sales) {
        this.sales = sales == null ? 0L : sales;
        touch();
    }

    public boolean isSearchable() {
        return status != null && status == 1;
    }
}
