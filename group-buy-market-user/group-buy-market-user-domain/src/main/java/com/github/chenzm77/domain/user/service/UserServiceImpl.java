package com.github.chenzm77.domain.user.service;

import com.github.chenzm77.domain.user.adapter.repository.IUserRepository;
import com.github.chenzm77.domain.user.model.entity.UserEntity;
import com.github.chenzm77.types.enums.ResponseCode;
import com.github.chenzm77.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserRepository userRepository;

    @Override
    public UserEntity queryUserById(Long userId) {
        UserEntity user = userRepository.queryUserById(userId);
        if (user == null) {
            throw new AppException(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getInfo());
        }
        return user;
    }

    @Override
    public UserEntity queryUserByPhone(String phone) {
        return userRepository.queryUserByPhone(phone);
    }

    @Override
    public void updateProfile(Long userId, String nickname, String avatar) {
        UserEntity user = userRepository.queryUserById(userId);
        if (user == null) {
            throw new AppException(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getInfo());
        }
        user.updateProfile(nickname, avatar);
        userRepository.updateUser(user);
    }

    @Override
    public List<UserEntity> batchQueryUsers(List<Long> userIds) {
        return userRepository.batchQueryUsers(userIds);
    }
}