package com.test.dio.biz.service;

import com.test.dio.biz.config.BizConfig;
import com.test.dio.biz.consts.Constant;
import com.test.dio.biz.reptile.service.ReptileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BizConfig.class)
@ActiveProfiles("dev")
public class ReptileServiceTest {

    @Autowired
    private ReptileService reptileService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @Ignore
    public void test() {
//        reptileService.reptileJob(Constant.MAELSTROM_URL, Constant.PAGE_LIMIT);
    }

    @Test
    @Ignore
    public void testRedis() {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        String value = hash.get(Constant.MAELSTROM, "https://bbs.nga.cn/read.php?tid=16170310");
        System.out.println(value);
    }

    @Test
//    @Ignore
    public void testMD5() {
        String s = DigestUtils.md5Hex(100009L + "adw");
        System.out.println(s);
    }
}
