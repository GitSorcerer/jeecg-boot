package org.spring.config;

import org.spring.model.OneModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: GH
 * @Date: 2019/12/1 21:55
 * @Version 1.0
 */
@ComponentScan("org.spring")
@Configuration
public class Config {
    @Bean
    public OneModel getOneModel() {
        return new OneModel();
    }
}
