package com.github.chenzm77.goods.domain.activity.service;

import com.github.chenzm77.goods.domain.activity.model.aggregate.Activity;
import com.github.chenzm77.goods.domain.activity.model.entity.ActivityItem;

public interface ActivityDomainService {

    Activity getCurrentActivity(Long spuId);

    void createActivity(Activity activity);

    void updateActivity(Activity activity);

    void deleteActivity(Long activityId);

    void createActivityItem(ActivityItem item);

    void updateActivityItem(ActivityItem item);

    void deleteActivityItem(Long activityItemId);
}
