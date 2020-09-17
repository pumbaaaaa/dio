package com.test.dio.sql;

import com.test.dio.biz.config.BizConfig;
import com.test.dio.biz.util.ModuConfProvider;
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
}
