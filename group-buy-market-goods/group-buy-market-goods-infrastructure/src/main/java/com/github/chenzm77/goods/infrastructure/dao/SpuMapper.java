package com.github.chenzm77.goods.infrastructure.dao;

import com.github.chenzm77.goods.infrastructure.dao.po.SpuPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpuMapper {
    SpuPO selectById(Long id);
    void insert(SpuPO po);
    void update(SpuPO po);
    void deleteById(Long id);
}
