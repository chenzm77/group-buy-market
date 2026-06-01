package com.github.chenzm77.trigger.rpc;

import com.github.chenzm77.api.IUserService;
import com.github.chenzm77.api.dto.*;
import com.github.chenzm77.api.response.Response;
import com.github.chenzm77.app.user.UserApplicationService;
import com.github.chenzm77.domain.user.model.entity.AddressEntity;
import com.github.chenzm77.domain.user.model.entity.UserEntity;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@DubboService(version = "1.0.0")
public class UserServiceRpc implements IUserService {

    @Resource
    private UserApplicationService userApplicationService;

    /**
     * 通过userId查询用户
     * @param userId
     * @return
     */
    @Override
    public UserResponseDTO queryUserById(Long userId) {
        UserEntity user = userApplicationService.queryUserById(userId);
        return toUserDTO(user);
    }

    /**
     * 通过手机号查询用户
     * @param phone
     * @return
     */
    @Override
    public UserResponseDTO queryUserByPhone(String phone) {
        UserEntity user = userApplicationService.queryUserByPhone(phone);
        return toUserDTO(user);
    }

    /**
     * 更新用户信息
     * @param userId
     * @param request
     * @return
     */
    @Override
    public Response<Void> updateProfile(Long userId, UpdateProfileRequestDTO request) {
        userApplicationService.updateProfile(userId, request.getNickname(), request.getAvatar());
        return Response.success(null);
    }

    /**
     * 获取用户地址列表
     * @param userId
     * @return
     */

    @Override
    public Response<List<AddressResponseDTO>> listAddresses(Long userId) {
        List<AddressEntity> addresses = userApplicationService.listAddresses(userId);
        List<AddressResponseDTO> dtos = addresses.stream().map(this::toAddressDTO).collect(Collectors.toList());
        return Response.success(dtos);
    }

    /**
     * 添加新地址
     * @param userId
     * @param request
     * @return
     */
    @Override
    public Response<AddressResponseDTO> addAddress(Long userId, AddAddressRequestDTO request) {
        AddressEntity entity = new AddressEntity();
        entity.setReceiverName(request.getReceiverName());
        entity.setReceiverPhone(request.getReceiverPhone());
        entity.setProvince(request.getProvince());
        entity.setCity(request.getCity());
        entity.setDistrict(request.getDistrict());
        entity.setDetailAddress(request.getDetailAddress());
        AddressEntity saved = userApplicationService.addAddress(userId, entity);
        return Response.success(toAddressDTO(saved));
    }

    /**
     * 更新地址
     * @param userId
     * @param addressId
     * @param request
     * @return
     */
    @Override
    public Response<Void> updateAddress(Long userId, Long addressId, UpdateAddressRequestDTO request) {
        AddressEntity entity = new AddressEntity();
        entity.setReceiverName(request.getReceiverName());
        entity.setReceiverPhone(request.getReceiverPhone());
        entity.setProvince(request.getProvince());
        entity.setCity(request.getCity());
        entity.setDistrict(request.getDistrict());
        entity.setDetailAddress(request.getDetailAddress());
        userApplicationService.updateAddress(userId, addressId, entity);
        return Response.success(null);
    }


    /**
     * 删除地址
     * @param userId
     * @param addressId
     * @return
     */
    @Override
    public Response<Void> deleteAddress(Long userId, Long addressId) {
        userApplicationService.deleteAddress(userId, addressId);
        return Response.success(null);
    }

    /**
     * 设置默认地址
     * @param userId
     * @param addressId
     * @return
     */

    @Override
    public Response<Void> setDefaultAddress(Long userId, Long addressId) {
        userApplicationService.setDefaultAddress(userId, addressId);
        return Response.success(null);
    }

    /**
     * 批量查询用户
     * @param userIds
     * @return
     */

    @Override
    public Response<List<UserResponseDTO>> batchQueryUsers(List<Long> userIds) {
        List<UserEntity> users = userApplicationService.batchQueryUsers(userIds);
        List<UserResponseDTO> dtos = users.stream().map(this::toUserDTO).collect(Collectors.toList());
        return Response.success(dtos);
    }

    /**
     * UserEntity to UserResponseDTO
     * @param user
     * @return
     */

    private UserResponseDTO toUserDTO(UserEntity user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }

    private AddressResponseDTO toAddressDTO(AddressEntity address) {
        return AddressResponseDTO.builder()
                .id(address.getId())
                .userId(address.getUserId())
                .receiverName(address.getReceiverName())
                .receiverPhone(address.getReceiverPhone())
                .province(address.getProvince())
                .city(address.getCity())
                .district(address.getDistrict())
                .detailAddress(address.getDetailAddress())
                .isDefault(address.getIsDefault())
                .build();
    }
}