package com.test.dio.web.config;

import com.test.dio.biz.config.BizConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Import(BizConfig.class)
@ComponentScan("com.test.dio.web")
@EnableSwagger2
public class WebConfig {
}
