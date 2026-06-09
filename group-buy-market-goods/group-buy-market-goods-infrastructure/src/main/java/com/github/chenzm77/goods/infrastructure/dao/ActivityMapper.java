package com.github.chenzm77.goods.infrastructure.dao;

import com.github.chenzm77.goods.infrastructure.dao.po.ActivityPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityMapper {
    ActivityPO selectCurrentBySpuId(Long spuId);
    ActivityPO selectById(Long id);
    void insert(ActivityPO po);
    void update(ActivityPO po);
    void deleteById(Long id);
}
