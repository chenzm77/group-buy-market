package com.github.chenzm77.auth.types.common;

public class Constants {

    public static class RedisKey {
        public static final String SESSION_PREFIX = "session:";
        public static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    }

    public static class Cookie {
        public static final String SESSION_ID = "sessionId";
        public static final String REFRESH_TOKEN = "refreshToken";
        public static final String SESSION_PATH = "/";
        public static final String REFRESH_TOKEN_PATH = "/refresh";
    }
}
