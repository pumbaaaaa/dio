package com.test.dio.biz.strategy;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 柱状图-双轴组件
 */
@Component("fundLineBar")
public class DoubleHistogramStrategy extends DefaultStrategy implements ModuConfStrategy {

    @Override
    public Map<String, String> formValueStructAna(List<Map<String, Object>> param) {
        return super.formValueStructAna(param);
    }
}
