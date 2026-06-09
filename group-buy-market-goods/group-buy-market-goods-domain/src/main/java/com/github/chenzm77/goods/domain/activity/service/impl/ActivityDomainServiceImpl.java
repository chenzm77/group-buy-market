package com.github.chenzm77.goods.domain.activity.service.impl;

import com.github.chenzm77.goods.domain.activity.model.aggregate.Activity;
import com.github.chenzm77.goods.domain.activity.model.entity.ActivityItem;
import com.github.chenzm77.goods.domain.activity.repository.ActivityRepository;
import com.github.chenzm77.goods.domain.activity.service.ActivityDomainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActivityDomainServiceImpl implements ActivityDomainService {

    @Resource
    private ActivityRepository activityRepository;

    @Override
    public Activity getCurrentActivity(Long spuId) {
        return activityRepository.findCurrentBySpuId(spuId);
    }

    @Override
    public void createActivity(Activity activity) {
        activity.create();
        activityRepository.save(activity);
    }

    @Override
    public void updateActivity(Activity activity) {
        activityRepository.update(activity);
    }

    @Override
    public void deleteActivity(Long activityId) {
        activityRepository.delete(activityId);
    }

    @Override
    public void createActivityItem(ActivityItem item) {
        activityRepository.saveItem(item);
    }

    @Override
    public void updateActivityItem(ActivityItem item) {
        activityRepository.updateItem(item);
    }

    @Override
    public void deleteActivityItem(Long activityItemId) {
        activityRepository.deleteItem(activityItemId);
    }
}
