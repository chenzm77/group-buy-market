package com.github.chenzm77.domain.user.adapter.repository;

import com.github.chenzm77.domain.user.model.entity.AddressEntity;

import java.util.List;

public interface IAddressRepository {

    List<AddressEntity> queryByUserId(Long userId);

    AddressEntity queryById(Long addressId);

    boolean save(AddressEntity addressEntity);

    boolean update(AddressEntity addressEntity);

    boolean delete(Long addressId, Long userId);

    boolean clearDefault(Long userId);

    boolean setDefault(Long addressId, Long userId);
}