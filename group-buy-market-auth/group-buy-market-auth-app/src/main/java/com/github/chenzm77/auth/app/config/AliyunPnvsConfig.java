package com.github.chenzm77.auth.app.config;

import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class AliyunPnvsConfig {

    @Resource
    private AliyunPnvsProperties properties;

    @Bean
    public Client aliyunPnvsClient() throws Exception {
        Config config = new Config();
        config.setAccessKeyId(properties.getAccessKeyId());
        config.setAccessKeySecret(properties.getAccessKeySecret());
        config.endpoint = properties.getEndpoint();
        return new Client(config);
    }
}
