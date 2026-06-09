package com.github.chenzm77.goods.app;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.github.chenzm77.goods")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
