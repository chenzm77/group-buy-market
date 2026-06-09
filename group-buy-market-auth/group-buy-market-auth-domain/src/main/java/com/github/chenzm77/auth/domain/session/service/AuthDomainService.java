package com.github.chenzm77.auth.domain.session.service;

import com.github.chenzm77.auth.domain.session.model.aggregate.AuthSession;

public interface AuthDomainService {

    void sendVerifyCode(String phone);

    SessionCreateResult login(String phone, String code);

    SessionCreateResult register(String phone, String code);

    SessionCreateResult refresh(String refreshTokenId);

    void logout(String sessionId, String refreshTokenId);

    class SessionCreateResult {
        private final AuthSession authSession;
        private final String refreshTokenId;

        public SessionCreateResult(AuthSession authSession, String refreshTokenId) {
            this.authSession = authSession;
            this.refreshTokenId = refreshTokenId;
        }

        public AuthSession getAuthSession() {
            return authSession;
        }

        public String getRefreshTokenId() {
            return refreshTokenId;
        }
    }
}
