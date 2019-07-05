package com.cato.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/16
 * @Description:
 */
@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer getCustomizer(){
        return configuration -> configuration.setMapUnderscoreToCamelCase(true);
    }
}
