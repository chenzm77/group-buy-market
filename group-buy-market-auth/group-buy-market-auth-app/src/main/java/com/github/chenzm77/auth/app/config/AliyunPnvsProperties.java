package com.github.chenzm77.auth.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.pnvs")
public class AliyunPnvsProperties {

    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint = "dypnsapi.aliyuncs.com";
    private String schemeCode;
    private String templateCode = "SMS_198470532";
}
