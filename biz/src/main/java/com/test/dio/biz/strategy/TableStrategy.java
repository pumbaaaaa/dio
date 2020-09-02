package com.test.dio.biz.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 表单组件类型策略
 *
 * <p>@Component beanName为前端传入参数componentName
 */
@Component("inputTable")
@Slf4j
public class TableStrategy extends DefaultStrategy implements ModuConfStrategy {

    @Override
    public Map<String, String> formValueStructAna(List<Map<String, Object>> param) {

        log.info("===========TableStrategy============");


        return null;
    }
}
