package com.github.chenzm77.goods.infrastructure.dao;

import com.github.chenzm77.goods.infrastructure.dao.po.ActivityItemPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityItemMapper {
    List<ActivityItemPO> selectByActivityId(Long activityId);
    void insert(ActivityItemPO po);
    void update(ActivityItemPO po);
    void deleteById(Long id);
}
