package com.github.chenzm77.user.domain.user.service.impl;

import com.github.chenzm77.user.domain.user.repository.IAddressRepository;
import com.github.chenzm77.user.domain.user.repository.IUserRepository;
import com.github.chenzm77.user.domain.user.model.entity.AddressEntity;
import com.github.chenzm77.user.domain.user.model.entity.UserEntity;
import com.github.chenzm77.user.domain.user.service.IAddressService;
import com.github.chenzm77.user.types.common.Constants;
import com.github.chenzm77.user.types.enums.ResponseCode;
import com.github.chenzm77.user.types.exception.AppException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Resource
    private IAddressRepository addressRepository;

    @Resource
    private IUserRepository userRepository;

    @Override
    public List<AddressEntity> listAddresses(Long userId) {
        UserEntity user = userRepository.queryUserById(userId);
        if (user == null) {
            throw new AppException(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getInfo());
        }
        return addressRepository.queryByUserId(userId);
    }

    @Override
    public AddressEntity addAddress(Long userId, AddressEntity address) {
        UserEntity user = userRepository.queryUserById(userId);
        if (user == null) {
            throw new AppException(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getInfo());
        }
        address.setUserId(userId);
        List<AddressEntity> existing = addressRepository.queryByUserId(userId);
        if (existing == null || existing.isEmpty()) {
            address.setIsDefault(Constants.AddressConst.IS_DEFAULT);
        } else {
            address.setIsDefault(Constants.AddressConst.NOT_DEFAULT);
        }
        addressRepository.save(address);
        return address;
    }

    @Override
    public void updateAddress(Long userId, Long addressId, AddressEntity address) {
        AddressEntity exist = addressRepository.queryById(addressId);
        if (exist == null || !exist.getUserId().equals(userId)) {
            throw new AppException(ResponseCode.ADDRESS_NOT_FOUND.getCode(), ResponseCode.ADDRESS_NOT_FOUND.getInfo());
        }
        address.setId(addressId);
        address.setUserId(userId);
        address.setIsDefault(exist.getIsDefault());
        addressRepository.update(address);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        AddressEntity exist = addressRepository.queryById(addressId);
        if (exist == null || !exist.getUserId().equals(userId)) {
            throw new AppException(ResponseCode.ADDRESS_NOT_FOUND.getCode(), ResponseCode.ADDRESS_NOT_FOUND.getInfo());
        }
        addressRepository.delete(addressId, userId);
    }

    @Override
    public void setDefaultAddress(Long userId, Long addressId) {
        AddressEntity exist = addressRepository.queryById(addressId);
        if (exist == null || !exist.getUserId().equals(userId)) {
            throw new AppException(ResponseCode.ADDRESS_NOT_FOUND.getCode(), ResponseCode.ADDRESS_NOT_FOUND.getInfo());
        }
        addressRepository.clearDefault(userId);
        addressRepository.setDefault(addressId, userId);
    }
}