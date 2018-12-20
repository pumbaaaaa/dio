package com.test.dio.biz.service;

import com.test.dio.biz.config.BizConfig;
import com.test.dio.biz.util.CommonUtil;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BizConfig.class)
@ActiveProfiles("dev")
public class ReptileServiceTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ReptileService reptileService;

    @Test
//    @Ignore
    public void test() {
        reptileService.maelstrom();
    }

    @Test
    public void testHtml() throws IOException {
        List<String> repliesPage = CommonUtil.getRepliesUrl("https://bbs.nga.cn/read.php?tid=15942466&_ff=-7", 118345L, 100000L);
        System.out.println(repliesPage);
    }

    @Test
//    @Ignore
    public void testDate() {
        Date date = LocalDateTime.parse("2018-11-22 18:19", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")).toDate();
        System.out.println(date);

        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        opsHash.put("aaa", "bbb", "0");
    }
}
