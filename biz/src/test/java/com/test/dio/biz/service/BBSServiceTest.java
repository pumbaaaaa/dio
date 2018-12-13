package com.test.dio.biz.service;

import com.test.dio.biz.config.BizConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BizConfig.class)
@ActiveProfiles("dev")
public class BBSServiceTest {

    @Autowired
    private BBSService bbsService;

    @Test
//    @Ignore
    public void test() throws IOException {
        bbsService.maelstrom();
    }
}
