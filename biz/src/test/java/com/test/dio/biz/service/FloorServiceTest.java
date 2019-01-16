package com.test.dio.biz.service;

import com.test.dio.biz.config.BizConfig;
import com.test.dio.biz.consts.Constant;
import com.test.dio.biz.reptile.service.FloorService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Test
    @Ignore
    public void testRegex() {
        String str = "大家都越来越好 [s:pst:举手]";
        String str1 = "[b]Reply to [tid=15929069]Topic[/tid] Post by [uid=6321910]牧纷纷[/uid] (2018-12-18 11:49)[/b] 大家新年快乐！";
        Pattern pattern = Pattern.compile(Constant.LABEL_REGEX);
        Matcher matcher = pattern.matcher(str1);
        List<String> urls = new ArrayList<>();
        while (matcher.find()) {
            urls.add(matcher.group());
        }
        System.out.println(urls);
    }
}
