package com.github.chenzm77.auth.domain.session.repository;

import com.github.chenzm77.auth.domain.session.model.aggregate.AuthSession;
import com.github.chenzm77.auth.domain.session.model.entity.RefreshToken;

public interface AuthSessionRepository {

    void saveSession(AuthSession authSession, long ttlSeconds);

    AuthSession findSession(String sessionId);

    void deleteSession(String sessionId);

    void saveRefreshToken(RefreshToken refreshToken, long ttlSeconds);

    RefreshToken findRefreshToken(String refreshTokenId);

    void deleteRefreshToken(String refreshTokenId);
}
