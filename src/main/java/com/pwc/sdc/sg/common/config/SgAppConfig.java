package com.pwc.sdc.sg.common.config;

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

    
}