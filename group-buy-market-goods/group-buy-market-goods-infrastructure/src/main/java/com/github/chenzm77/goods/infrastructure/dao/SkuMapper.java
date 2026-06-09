package com.github.chenzm77.goods.infrastructure.dao;

import com.github.chenzm77.goods.infrastructure.dao.po.SkuPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SkuMapper {
    List<SkuPO> selectBySpuId(Long spuId);
    void insert(SkuPO po);
    void update(SkuPO po);
    void deleteById(Long id);
}
