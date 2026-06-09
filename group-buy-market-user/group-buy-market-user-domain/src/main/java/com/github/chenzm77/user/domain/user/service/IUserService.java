package com.github.chenzm77.user.domain.user.service;

import com.github.chenzm77.user.domain.user.model.entity.UserEntity;

import java.util.List;

public interface IUserService {

    UserEntity queryUserById(Long userId);

    UserEntity queryUserByPhone(String phone);

    UserEntity createUserByPhone(String phone);

    void updateProfile(Long userId, String nickname, String avatar);

    List<UserEntity> batchQueryUsers(List<Long> userIds);
}