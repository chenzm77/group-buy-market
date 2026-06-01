package com.github.chenzm77.domain.user.service;

import com.github.chenzm77.domain.user.model.entity.AddressEntity;
import com.github.chenzm77.domain.user.model.entity.UserEntity;

import java.util.List;

public interface IUserService {

    UserEntity queryUserById(Long userId);

    UserEntity queryUserByPhone(String phone);

    void updateProfile(Long userId, String nickname, String avatar);

    List<UserEntity> batchQueryUsers(List<Long> userIds);
}