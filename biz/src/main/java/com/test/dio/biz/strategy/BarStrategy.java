package com.test.dio.biz.strategy;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 条形组件
 */
@Component("horizontalBarTop15")
public class BarStrategy extends DefaultStrategy implements ModuConfStrategy{

    @Override
    public Map<String, String> formValueStructAna(List<Map<String, Object>> param) {
        // TODO 副指标formValue结构为{"_title", "_data"}
        return super.formValueStructAna(param);
    }
}
