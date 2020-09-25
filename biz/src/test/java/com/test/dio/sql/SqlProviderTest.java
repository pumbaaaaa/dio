package com.test.dio.sql;

import com.test.dio.biz.config.BizConfig;
import com.test.dio.biz.util.ModuConfProvider;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = BizConfig.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class SqlProviderTest {

    @Autowired
    private ModuConfProvider moduConfProvider;


    @Test
    public void testRightJoin() {
        String sql = moduConfProvider.validScriptSql();
        System.out.println(sql);
    }

    @Test
    public void testReplace() {
        String sql = "aaaa < d2d2";
        String s = sql.replaceAll("<", "&lt;");
        System.out.println(s);
    }

    @Test
    public void testLocalDateTime() {
        String now = LocalDateTime.now().toString("yyyy-MM-dd HH:mm:00");


        LocalDateTime parse = LocalDateTime.parse(now, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println(parse);
    }
}
