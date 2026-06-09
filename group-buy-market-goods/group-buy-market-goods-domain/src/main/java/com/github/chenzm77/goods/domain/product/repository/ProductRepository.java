package com.github.chenzm77.goods.domain.product.repository;

import com.github.chenzm77.goods.domain.product.model.aggregate.Spu;
import com.github.chenzm77.goods.domain.product.model.entity.Sku;

public interface ProductRepository {

    Spu findById(Long spuId);

    void save(Spu spu);

    void update(Spu spu);

    void delete(Long spuId);

    void saveSku(Sku sku);

    void updateSku(Sku sku);

    void deleteSku(Long skuId);
}
