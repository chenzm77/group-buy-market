package com.github.chenzm77.goods.domain.activity.service;

import com.github.chenzm77.goods.domain.activity.model.Activity;

public interface ActivityDomainService {

    Activity getCurrentActivity(Long spuId);
}
