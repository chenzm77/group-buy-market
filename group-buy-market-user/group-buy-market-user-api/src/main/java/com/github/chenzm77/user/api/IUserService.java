package com.github.chenzm77.user.api;

import com.github.chenzm77.user.api.dto.*;
import com.github.chenzm77.user.api.response.Response;

import java.util.List;

public interface IUserService {

    UserResponseDTO queryUserById(Long userId);

    UserResponseDTO queryUserByPhone(String phone);

    UserResponseDTO createUserByPhone(String phone);

    Response<Void> updateProfile(Long userId, UpdateProfileRequestDTO request);

    Response<List<AddressResponseDTO>> listAddresses(Long userId);

    Response<AddressResponseDTO> addAddress(Long userId, AddAddressRequestDTO request);

    Response<Void> updateAddress(Long userId, Long addressId, UpdateAddressRequestDTO request);

    Response<Void> deleteAddress(Long userId, Long addressId);

    Response<Void> setDefaultAddress(Long userId, Long addressId);

    Response<List<UserResponseDTO>> batchQueryUsers(List<Long> userIds);
}