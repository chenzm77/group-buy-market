package com.github.chenzm77.auth.domain.session.service.impl;

import com.github.chenzm77.auth.domain.session.acl.SmsVerifyProvider;
import com.github.chenzm77.auth.domain.session.acl.UserProfileProvider;
import com.github.chenzm77.auth.domain.session.model.aggregate.AuthSession;
import com.github.chenzm77.auth.domain.session.model.entity.RefreshToken;
import com.github.chenzm77.auth.domain.session.model.valueobjects.PhoneNumber;
import com.github.chenzm77.auth.domain.session.model.valueobjects.UserPrincipal;
import com.github.chenzm77.auth.domain.session.repository.AuthSessionRepository;
import com.github.chenzm77.auth.domain.session.service.AuthDomainService;
import com.github.chenzm77.auth.types.enums.ResponseCode;
import com.github.chenzm77.auth.types.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthDomainServiceImpl implements AuthDomainService {

    private static final long SESSION_TTL_SECONDS = 30 * 60L;
    private static final long REFRESH_TOKEN_TTL_SECONDS = 7 * 24 * 60 * 60L;

    @Resource
    private SmsVerifyProvider smsVerifyProvider;

    @Resource
    private UserProfileProvider userProfileProvider;

    @Resource
    private AuthSessionRepository authSessionRepository;

    @Override
    public void sendVerifyCode(String phone) {
        smsVerifyProvider.sendVerifyCode(new PhoneNumber(phone));
    }

    @Override
    public SessionCreateResult login(String phone, String code) {
        PhoneNumber phoneNumber = new PhoneNumber(phone);
        checkCode(phoneNumber, code);
        UserPrincipal userPrincipal = userProfileProvider.findByPhone(phoneNumber);
        if (userPrincipal == null) {
            throw new AppException(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getInfo());
        }
        return createSession(userPrincipal);
    }

    @Override
    public SessionCreateResult register(String phone, String code) {
        PhoneNumber phoneNumber = new PhoneNumber(phone);
        checkCode(phoneNumber, code);
        UserPrincipal exists = userProfileProvider.findByPhone(phoneNumber);
        if (exists != null) {
            throw new AppException(ResponseCode.USER_ALREADY_EXISTS.getCode(), ResponseCode.USER_ALREADY_EXISTS.getInfo());
        }
        UserPrincipal created = userProfileProvider.createByPhone(phoneNumber);
        return createSession(created);
    }

    @Override
    public SessionCreateResult refresh(String refreshTokenId) {
        if (StringUtils.isBlank(refreshTokenId)) {
            throw new AppException(ResponseCode.REFRESH_TOKEN_INVALID.getCode(), ResponseCode.REFRESH_TOKEN_INVALID.getInfo());
        }
        RefreshToken refreshToken = authSessionRepository.findRefreshToken(refreshTokenId);
        if (refreshToken == null) {
            throw new AppException(ResponseCode.REFRESH_TOKEN_INVALID.getCode(), ResponseCode.REFRESH_TOKEN_INVALID.getInfo());
        }
        UserPrincipal userPrincipal = userProfileProvider.findById(refreshToken.getUserId());
        if (userPrincipal == null) {
            throw new AppException(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getInfo());
        }
        authSessionRepository.deleteRefreshToken(refreshTokenId);
        return createSession(userPrincipal);
    }

    @Override
    public void logout(String sessionId, String refreshTokenId) {
        if (StringUtils.isNotBlank(sessionId)) {
            authSessionRepository.deleteSession(sessionId);
        }
        if (StringUtils.isNotBlank(refreshTokenId)) {
            authSessionRepository.deleteRefreshToken(refreshTokenId);
        }
    }

    private void checkCode(PhoneNumber phoneNumber, String code) {
        if (StringUtils.isBlank(code) || !smsVerifyProvider.checkVerifyCode(phoneNumber, code)) {
            throw new AppException(ResponseCode.VERIFY_CODE_ERROR.getCode(), ResponseCode.VERIFY_CODE_ERROR.getInfo());
        }
    }

    private SessionCreateResult createSession(UserPrincipal userPrincipal) {
        AuthSession authSession = AuthSession.create(userPrincipal);
        RefreshToken refreshToken = authSession.issueRefreshToken();
        authSessionRepository.saveSession(authSession, SESSION_TTL_SECONDS);
        authSessionRepository.saveRefreshToken(refreshToken, REFRESH_TOKEN_TTL_SECONDS);
        return new SessionCreateResult(authSession, refreshToken.getRefreshTokenId());
    }
}
