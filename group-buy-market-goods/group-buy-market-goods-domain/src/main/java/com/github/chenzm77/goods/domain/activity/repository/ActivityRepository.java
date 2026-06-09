package com.github.chenzm77.goods.domain.activity.repository;

import com.github.chenzm77.goods.domain.activity.model.Activity;

public interface ActivityRepository {

    Activity findCurrentBySpuId(Long spuId);

    Activity findById(Long activityId);

    void save(Activity activity);

    void update(Activity activity);

    void delete(Long activityId);
}
