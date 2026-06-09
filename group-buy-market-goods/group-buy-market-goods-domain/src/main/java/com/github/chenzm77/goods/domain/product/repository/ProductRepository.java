package com.github.chenzm77.goods.domain.product.repository;

import com.github.chenzm77.goods.domain.product.model.Spu;

public interface ProductRepository {

    Spu findById(Long spuId);

    void save(Spu spu);

    void update(Spu spu);

    void delete(Long spuId);
}
