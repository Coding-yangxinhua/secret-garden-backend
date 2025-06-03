package com.pwc.sdc.sg.common.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xinhua X Yang
 */
@Configuration
@MapperScan(basePackages = "com.pwc.sdc.sg.mapper")
@ConfigurationProperties("system")
@Getter
@Setter
public class SgAppConfig {

    private WechatConfig wechatConfig;

    @Data
    public static class WechatConfig {

        private String userId;

        private String appId;

        private String appSecret;

        private String encodingAesKey;

        private String token;
    }
}