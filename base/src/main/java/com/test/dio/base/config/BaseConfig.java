package com.test.dio.base.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.test.dio.base")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BaseConfig {
}
