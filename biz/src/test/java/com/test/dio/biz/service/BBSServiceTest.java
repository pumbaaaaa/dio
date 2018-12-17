package com.test.dio.biz.service;

import com.google.common.hash.Hashing;
import com.test.dio.biz.config.BizConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    @Test
    public void testMD5() {
        String input = "pengsyhx@gmail.com" + null + "psy";
        long dStart = System.currentTimeMillis();
        String s1 = DigestUtils.md5Hex(input.getBytes(StandardCharsets.UTF_8));
        long dEnd = System.currentTimeMillis();
        long dTime = dEnd - dStart;

        long hStart = System.currentTimeMillis();
        String s2 = Hashing.md5().hashString(input, StandardCharsets.UTF_8).toString();
        long hEnd = System.currentTimeMillis();
        long hTime = hEnd - hStart;

        System.out.println("d=====>" + s1 + ", Time =====>" + dTime);
        System.out.println("h=====>" + s2 + ", Time =====>" + hTime);
    }
}
