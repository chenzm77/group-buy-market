package com.github.chenzm77.domain.user.service;

import com.github.chenzm77.domain.user.model.entity.AddressEntity;

import java.util.List;

public interface IAddressService {

    List<AddressEntity> listAddresses(Long userId);

    AddressEntity addAddress(Long userId, AddressEntity address);

    void updateAddress(Long userId, Long addressId, AddressEntity address);

    void deleteAddress(Long userId, Long addressId);

    void setDefaultAddress(Long userId, Long addressId);
}