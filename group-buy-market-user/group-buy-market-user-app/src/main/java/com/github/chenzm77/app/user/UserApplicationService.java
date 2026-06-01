package com.github.chenzm77.app.user;

import com.github.chenzm77.domain.user.model.entity.AddressEntity;
import com.github.chenzm77.domain.user.model.entity.UserEntity;
import com.github.chenzm77.domain.user.service.IAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserApplicationService {

    @Resource
    private com.github.chenzm77.domain.user.service.IUserService userService;

    @Resource
    private IAddressService addressService;

    public UserEntity queryUserById(Long userId) {
        return userService.queryUserById(userId);
    }

    public UserEntity queryUserByPhone(String phone) {
        return userService.queryUserByPhone(phone);
    }

    public void updateProfile(Long userId, String nickname, String avatar) {
        userService.updateProfile(userId, nickname, avatar);
    }

    public List<AddressEntity> listAddresses(Long userId) {
        return addressService.listAddresses(userId);
    }

    public AddressEntity addAddress(Long userId, AddressEntity entity) {
        return addressService.addAddress(userId, entity);
    }

    public void updateAddress(Long userId, Long addressId, AddressEntity entity) {
        addressService.updateAddress(userId, addressId, entity);
    }

    public void deleteAddress(Long userId, Long addressId) {
        addressService.deleteAddress(userId, addressId);
    }

    public void setDefaultAddress(Long userId, Long addressId) {
        addressService.setDefaultAddress(userId, addressId);
    }

    public List<UserEntity> batchQueryUsers(List<Long> userIds) {
        return userService.batchQueryUsers(userIds);
    }
}