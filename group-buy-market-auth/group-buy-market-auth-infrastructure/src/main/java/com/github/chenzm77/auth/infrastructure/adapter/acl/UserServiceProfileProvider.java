package com.github.chenzm77.auth.infrastructure.adapter.acl;

import com.github.chenzm77.auth.domain.session.acl.UserProfileProvider;
import com.github.chenzm77.auth.domain.session.model.valueobjects.PhoneNumber;
import com.github.chenzm77.auth.domain.session.model.valueobjects.UserPrincipal;
import com.github.chenzm77.user.api.IUserService;
import com.github.chenzm77.user.api.dto.UserResponseDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class UserServiceProfileProvider implements UserProfileProvider {

    @DubboReference(version = "1.0.0", check = false)
    private IUserService userService;

    @Override
    public UserPrincipal findByPhone(PhoneNumber phoneNumber) {
        return toPrincipal(userService.queryUserByPhone(phoneNumber.getValue()));
    }

    @Override
    public UserPrincipal findById(Long userId) {
        return toPrincipal(userService.queryUserById(userId));
    }

    @Override
    public UserPrincipal createByPhone(PhoneNumber phoneNumber) {
        return toPrincipal(userService.createUserByPhone(phoneNumber.getValue()));
    }

    private UserPrincipal toPrincipal(UserResponseDTO dto) {
        if (dto == null) {
            return null;
        }
        return UserPrincipal.builder()
                .userId(dto.getId())
                .phone(dto.getPhone())
                .nickname(dto.getNickname())
                .avatar(dto.getAvatar())
                .role(dto.getRole())
                .status(dto.getStatus())
                .build();
    }
}
