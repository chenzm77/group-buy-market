package com.github.chenzm77.goods.domain.activity.repository;

import com.github.chenzm77.goods.domain.activity.model.aggregate.Activity;
import com.github.chenzm77.goods.domain.activity.model.entity.ActivityItem;

public interface ActivityRepository {

    Activity findCurrentBySpuId(Long spuId);

    Activity findById(Long activityId);

    void save(Activity activity);

    void update(Activity activity);

    void delete(Long activityId);

    void saveItem(ActivityItem item);

    void updateItem(ActivityItem item);

    void deleteItem(Long activityItemId);
}
