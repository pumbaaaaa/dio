package com.test.dio.biz.config;

import com.test.dio.base.config.BaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.test.dio.biz")
@Import(BaseConfig.class)
public class BizConfig {
}
