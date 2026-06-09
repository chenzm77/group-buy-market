package com.github.chenzm77.goods.infrastructure.adapter.repository;

import com.github.chenzm77.goods.domain.activity.model.aggregate.Activity;
import com.github.chenzm77.goods.domain.activity.model.entity.ActivityItem;
import com.github.chenzm77.goods.domain.activity.repository.ActivityRepository;
import com.github.chenzm77.goods.infrastructure.dao.ActivityItemMapper;
import com.github.chenzm77.goods.infrastructure.dao.ActivityMapper;
import com.github.chenzm77.goods.infrastructure.dao.po.ActivityItemPO;
import com.github.chenzm77.goods.infrastructure.dao.po.ActivityPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivityItemMapper activityItemMapper;

    @Override
    public Activity findCurrentBySpuId(Long spuId) {
        ActivityPO po = activityMapper.selectCurrentBySpuId(spuId);
        return toEntity(po);
    }

    @Override
    public Activity findById(Long activityId) {
        ActivityPO po = activityMapper.selectById(activityId);
        return toEntity(po);
    }

    @Override
    public void save(Activity activity) {
        activityMapper.insert(toPO(activity));
        if (activity.getItems() != null) {
            activity.getItems().forEach(item -> activityItemMapper.insert(toPO(item)));
        }
    }

    @Override
    public void update(Activity activity) {
        activityMapper.update(toPO(activity));
    }

    @Override
    public void delete(Long activityId) {
        activityMapper.deleteById(activityId);
    }

    @Override
    public void saveItem(ActivityItem item) {
        activityItemMapper.insert(toPO(item));
    }

    @Override
    public void updateItem(ActivityItem item) {
        activityItemMapper.update(toPO(item));
    }

    @Override
    public void deleteItem(Long activityItemId) {
        activityItemMapper.deleteById(activityItemId);
    }

    private Activity toEntity(ActivityPO po) {
        if (po == null) {
            return null;
        }
        List<ActivityItemPO> itemPOS = activityItemMapper.selectByActivityId(po.getId());
        return Activity.builder()
                .id(po.getId())
                .spuId(po.getSpuId())
                .name(po.getName())
                .startTime(po.getStartTime())
                .endTime(po.getEndTime())
                .groupSize(po.getGroupSize())
                .status(po.getStatus())
                .createTime(po.getCreateTime())
                .items(itemPOS == null ? Collections.emptyList() : itemPOS.stream().map(this::toEntity).collect(Collectors.toList()))
                .build();
    }

    private ActivityItem toEntity(ActivityItemPO po) {
        return ActivityItem.builder()
                .id(po.getId())
                .activityId(po.getActivityId())
                .skuId(po.getSkuId())
                .seckillPrice(po.getSeckillPrice())
                .perUserLimit(po.getPerUserLimit())
                .createTime(po.getCreateTime())
                .build();
    }

    private ActivityPO toPO(Activity activity) {
        ActivityPO po = new ActivityPO();
        po.setId(activity.getId());
        po.setSpuId(activity.getSpuId());
        po.setName(activity.getName());
        po.setStartTime(activity.getStartTime());
        po.setEndTime(activity.getEndTime());
        po.setGroupSize(activity.getGroupSize());
        po.setStatus(activity.getStatus());
        po.setCreateTime(activity.getCreateTime());
        return po;
    }

    private ActivityItemPO toPO(ActivityItem item) {
        ActivityItemPO po = new ActivityItemPO();
        po.setId(item.getId());
        po.setActivityId(item.getActivityId());
        po.setSkuId(item.getSkuId());
        po.setSeckillPrice(item.getSeckillPrice());
        po.setPerUserLimit(item.getPerUserLimit());
        po.setCreateTime(item.getCreateTime());
        return po;
    }
}
