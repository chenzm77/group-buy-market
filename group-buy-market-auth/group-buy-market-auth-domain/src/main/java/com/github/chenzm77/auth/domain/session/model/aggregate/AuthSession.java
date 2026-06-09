package com.github.chenzm77.auth.domain.session.model.aggregate;

import com.github.chenzm77.auth.domain.session.model.entity.RefreshToken;
import com.github.chenzm77.auth.domain.session.model.valueobjects.UserPrincipal;
import com.github.chenzm77.auth.types.enums.ResponseCode;
import com.github.chenzm77.auth.types.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthSession implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sessionId;
    private Long userId;
    private String nickname;
    private String avatar;
    private String role;
    private Long loginTime;

    public static AuthSession create(UserPrincipal userPrincipal) {
        if (userPrincipal == null || userPrincipal.getUserId() == null) {
            throw new AppException(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getInfo());
        }
        if (userPrincipal.isBanned()) {
            throw new AppException(ResponseCode.USER_BANNED.getCode(), ResponseCode.USER_BANNED.getInfo());
        }
        return AuthSession.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(userPrincipal.getUserId())
                .nickname(userPrincipal.getNickname())
                .avatar(userPrincipal.getAvatar())
                .role(userPrincipal.getRole())
                .loginTime(Instant.now().getEpochSecond())
                .build();
    }

    public RefreshToken issueRefreshToken() {
        return RefreshToken.builder()
                .refreshTokenId(UUID.randomUUID().toString())
                .userId(userId)
                .issuedAt(Instant.now().getEpochSecond())
                .build();
    }
}
