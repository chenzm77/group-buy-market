package com.github.chenzm77.auth.trigger.rpc;

import com.github.chenzm77.auth.types.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class RpcContextCookieReader {

    private static final String COOKIE_ATTACHMENT = "Cookie";
    private static final String COOKIE_ATTACHMENT_LOWER = "cookie";

    public String getSessionId() {
        return getCookieValue(Constants.Cookie.SESSION_ID);
    }

    public String getRefreshToken() {
        return getCookieValue(Constants.Cookie.REFRESH_TOKEN);
    }

    private String getCookieValue(String name) {
        String directValue = getAttachment(name);
        if (StringUtils.isNotBlank(directValue)) {
            return directValue;
        }
        String cookie = getAttachment(COOKIE_ATTACHMENT);
        if (StringUtils.isBlank(cookie)) {
            cookie = getAttachment(COOKIE_ATTACHMENT_LOWER);
        }
        return parseCookie(cookie).get(name);
    }

    private String getAttachment(String key) {
        String value = RpcContext.getServerContext().getAttachment(key);
        if (StringUtils.isBlank(value)) {
            value = RpcContext.getContext().getAttachment(key);
        }
        return value;
    }

    private Map<String, String> parseCookie(String cookie) {
        Map<String, String> result = new HashMap<>();
        if (StringUtils.isBlank(cookie)) {
            return result;
        }
        String[] pairs = cookie.split(";");
        for (String pair : pairs) {
            int index = pair.indexOf('=');
            if (index <= 0) {
                continue;
            }
            String key = pair.substring(0, index).trim();
            String value = pair.substring(index + 1).trim();
            if (StringUtils.isNotBlank(key)) {
                result.put(key, decode(value));
            }
        }
        return result;
    }

    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
        } catch (Exception ignored) {
            return value;
        }
    }
}
