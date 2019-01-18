package com.test.dio.biz.service;

import com.test.dio.biz.config.BizConfig;
import com.test.dio.biz.reptile.service.FloorService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = BizConfig.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class FloorServiceTest {

    @Autowired
    private FloorService floorService;

    @Test
    @Ignore
    public void testFloor() {
        floorService.getPostAllFloor();
    }
}
