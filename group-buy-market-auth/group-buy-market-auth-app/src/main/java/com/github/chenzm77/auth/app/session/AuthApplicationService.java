package com.github.chenzm77.auth.app.session;

import com.github.chenzm77.auth.api.dto.AuthSessionResponseDTO;
import com.github.chenzm77.auth.app.config.CookieWriter;
import com.github.chenzm77.auth.domain.session.model.aggregate.AuthSession;
import com.github.chenzm77.auth.domain.session.service.AuthDomainService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service
public class AuthApplicationService {

    @Resource
    private AuthDomainService authDomainService;

    @Resource
    private CookieWriter cookieWriter;

    public void sendVerifyCode(String phone) {
        authDomainService.sendVerifyCode(phone);
    }


    public AuthSessionResponseDTO login(String phone, String code) {
        AuthDomainService.SessionCreateResult result = authDomainService.login(phone, code);
        cookieWriter.writeLoginCookies(result.getAuthSession().getSessionId(), result.getRefreshTokenId());
        return toResponse(result.getAuthSession());
    }


    public AuthSessionResponseDTO register(String phone, String code) {
        AuthDomainService.SessionCreateResult result = authDomainService.register(phone, code);
        cookieWriter.writeLoginCookies(result.getAuthSession().getSessionId(), result.getRefreshTokenId());
        return toResponse(result.getAuthSession());
    }


    public AuthSessionResponseDTO refresh(String refreshTokenId) {
        AuthDomainService.SessionCreateResult result = authDomainService.refresh(refreshTokenId);
        cookieWriter.writeLoginCookies(result.getAuthSession().getSessionId(), result.getRefreshTokenId());
        return toResponse(result.getAuthSession());
    }

    public void logout(String sessionId, String refreshTokenId) {
        authDomainService.logout(sessionId, refreshTokenId);
        cookieWriter.clearCookies();
    }

    private AuthSessionResponseDTO toResponse(AuthSession authSession) {
        return AuthSessionResponseDTO.builder()
                .userId(authSession.getUserId())
                .nickname(authSession.getNickname())
                .avatar(authSession.getAvatar())
                .role(authSession.getRole())
                .sessionId(authSession.getSessionId())
                .build();
    }
}
