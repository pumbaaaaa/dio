package com.test.dio.web;

import com.test.dio.web.config.WebConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebConfig.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
