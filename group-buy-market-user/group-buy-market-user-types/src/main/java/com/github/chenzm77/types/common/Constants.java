package com.github.chenzm77.types.common;

public class Constants {

    public static final class ResponseCode {
        public static final String SUCCESS = "0000";
        public static final String UNKNOWN_ERROR = "9999";
        public static final String PARAM_ERROR = "0001";
        public static final String USER_NOT_FOUND = "1001";
        public static final String ADDRESS_NOT_FOUND = "2001";
        public static final String SESSION_EXPIRED = "4002";
    }

    public static final class UserStatus {
        public static final int NORMAL = 1;
        public static final int BANNED = 0;
    }

    public static final class UserRole {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }

    public static final class AddressConst {
        public static final int IS_DEFAULT = 1;
        public static final int NOT_DEFAULT = 0;
    }

    public static final class TokenTTL {
        public static final long ACCESS_TOKEN_TTL = 30 * 60 * 1000L;
        public static final long REFRESH_TOKEN_TTL = 7 * 24 * 60 * 60 * 1000L;
        public static final long VERIFY_CODE_TTL = 5 * 60 * 1000L;
        public static final long USER_VERSION_TTL = 30 * 60 * 1000L;
    }

    public static final class RedisKey {
        public static final String TOKEN_BLACKLIST = "token:blacklist:";

        public static String refreshTokenKey(String refreshToken) {
            return "token:refresh:" + refreshToken;
        }

        public static String userVersionKey(Long userId) {
            return "token:version:" + userId;
        }

        public static String verifyCodeKey(String phone) {
            return "verify:code:" + phone;
        }
    }

    public static final class VerifyCodeConst {
        public static final int CODE_LENGTH = 6;
        public static final long CODE_TTL_SECONDS = 300;
    }
}