package com.github.chenzm77.auth.infrastructure.adapter.repository;

import com.github.chenzm77.auth.domain.session.model.aggregate.AuthSession;
import com.github.chenzm77.auth.domain.session.model.entity.RefreshToken;
import com.github.chenzm77.auth.domain.session.repository.AuthSessionRepository;
import com.github.chenzm77.auth.infrastructure.redis.RedisService;
import com.github.chenzm77.auth.types.common.Constants;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class AuthSessionRepositoryImpl implements AuthSessionRepository {

    @Resource
    private RedisService redisService;

    @Override
    public void saveSession(AuthSession authSession, long ttlSeconds) {
        redisService.setValue(Constants.RedisKey.SESSION_PREFIX + authSession.getSessionId(), authSession, ttlSeconds);
    }

    @Override
    public AuthSession findSession(String sessionId) {
        return redisService.getValue(Constants.RedisKey.SESSION_PREFIX + sessionId);
    }

    @Override
    public void deleteSession(String sessionId) {
        redisService.delete(Constants.RedisKey.SESSION_PREFIX + sessionId);
    }

    @Override
    public void saveRefreshToken(RefreshToken refreshToken, long ttlSeconds) {
        redisService.setValue(Constants.RedisKey.REFRESH_TOKEN_PREFIX + refreshToken.getRefreshTokenId(), refreshToken, ttlSeconds);
    }

    @Override
    public RefreshToken findRefreshToken(String refreshTokenId) {
        return redisService.getValue(Constants.RedisKey.REFRESH_TOKEN_PREFIX + refreshTokenId);
    }

    @Override
    public void deleteRefreshToken(String refreshTokenId) {
        redisService.delete(Constants.RedisKey.REFRESH_TOKEN_PREFIX + refreshTokenId);
    }
}
