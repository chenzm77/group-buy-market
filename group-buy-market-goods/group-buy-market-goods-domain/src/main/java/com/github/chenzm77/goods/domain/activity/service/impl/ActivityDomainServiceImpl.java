package com.github.chenzm77.goods.domain.activity.service.impl;

import com.github.chenzm77.goods.domain.activity.model.Activity;
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
}
