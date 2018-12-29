package com.test.dio.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PubService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void pub() {
        redisTemplate.convertAndSend("jojo", "dio");

    }
}
