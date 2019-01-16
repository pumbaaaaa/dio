package com.test.dio.job;

import com.test.dio.job.config.JobConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(JobConfig.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
