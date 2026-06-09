package com.github.chenzm77.auth.app.config;

import com.github.chenzm77.auth.types.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CookieWriter {

    private static final long SESSION_MAX_AGE = 30 * 60L;
    private static final long REFRESH_MAX_AGE = 7 * 24 * 60 * 60L;

    @Resource
    private CookieProperties cookieProperties;

    public void writeLoginCookies(String sessionId, String refreshTokenId) {
        RpcContext.getServerContext().setAttachment("Set-Cookie", buildCookie(Constants.Cookie.SESSION_ID, sessionId, Constants.Cookie.SESSION_PATH, SESSION_MAX_AGE));
        RpcContext.getServerContext().setAttachment("Set-Cookie-2", buildCookie(Constants.Cookie.REFRESH_TOKEN, refreshTokenId, Constants.Cookie.REFRESH_TOKEN_PATH, REFRESH_MAX_AGE));
    }

    public void clearCookies() {
        RpcContext.getServerContext().setAttachment("Set-Cookie", buildCookie(Constants.Cookie.SESSION_ID, "", Constants.Cookie.SESSION_PATH, 0));
        RpcContext.getServerContext().setAttachment("Set-Cookie-2", buildCookie(Constants.Cookie.REFRESH_TOKEN, "", Constants.Cookie.REFRESH_TOKEN_PATH, 0));
    }

    private String buildCookie(String name, String value, String path, long maxAge) {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append('=').append(value == null ? "" : value)
                .append("; HttpOnly")
                .append("; SameSite=").append(cookieProperties.getSameSite())
                .append("; Max-Age=").append(maxAge)
                .append("; Path=").append(path);
        if (cookieProperties.isSecure()) {
            builder.append("; Secure");
        }
        if (StringUtils.isNotBlank(cookieProperties.getDomain())) {
            builder.append("; Domain=").append(cookieProperties.getDomain());
        }
        return builder.toString();
    }
}
