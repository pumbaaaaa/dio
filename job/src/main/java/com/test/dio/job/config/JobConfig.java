package com.test.dio.job.config;

import com.test.dio.biz.config.BizConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import(BizConfig.class)
@ComponentScan("com.test.dio.job")
@EnableScheduling
public class JobConfig {
}
