package com.github.chenzm77.user.domain.user.repository;

import com.github.chenzm77.user.domain.user.model.entity.UserEntity;

public interface IUserRepository {

    UserEntity queryUserById(Long userId);

    UserEntity queryUserByPhone(String phone);

    boolean saveUser(UserEntity userEntity);

    boolean updateUser(UserEntity userEntity);

    java.util.List<UserEntity> batchQueryUsers(java.util.List<Long> userIds);
}