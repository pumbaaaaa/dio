package com.test.dio.web.rest;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class HelloCtr {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloCtr.class);

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String remoteHost = request.getRemoteHost();
        LOGGER.info("Request URI: {}, HOST: {}", requestURI, remoteHost);
        return RandomStringUtils.randomPrint(5);
    }
}
