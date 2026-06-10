package com.github.chenzm77.goods.domain.product.model;

import com.github.chenzm77.goods.domain.product.model.valueobjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sku implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long spuId;
    private String specJson;
    private Long price;
    private String image;
    private Integer status;

    public void changePrice(Money money) {
        this.price = money.getValue();
    }

    public void enable() {
        this.status = 1;
    }

    public void disable() {
        this.status = 0;
    }

    public boolean isEnabled() {
        return status != null && status == 1;
    }
}
