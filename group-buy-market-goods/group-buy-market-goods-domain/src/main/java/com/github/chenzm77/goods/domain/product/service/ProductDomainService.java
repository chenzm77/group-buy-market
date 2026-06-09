package com.github.chenzm77.goods.domain.product.service;

import com.github.chenzm77.goods.domain.product.model.Spu;

public interface ProductDomainService {

    Spu getProductDetail(Long spuId);

    void rebuildProductIndex(Long spuId);
}
