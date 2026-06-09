package com.github.chenzm77.auth.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "auth.cookie")
public class CookieProperties {

    private boolean secure = true;
    private String sameSite = "Lax";
    private String domain;
}
