package com.github.chenzm77.goods.domain.product.service;

import com.github.chenzm77.goods.domain.product.model.aggregate.Spu;
import com.github.chenzm77.goods.domain.product.model.entity.Sku;

public interface ProductDomainService {

    Spu getProductDetail(Long spuId);

    void createSpu(Spu spu);

    void updateSpu(Spu spu);

    void deleteSpu(Long spuId);

    void createSku(Sku sku);

    void updateSku(Sku sku);

    void deleteSku(Long skuId);

    void rebuildProductIndex(Long spuId);
}
