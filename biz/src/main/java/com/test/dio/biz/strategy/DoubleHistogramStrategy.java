package com.test.dio.biz.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 柱状图-双轴组件
 */
@Component("fundLineBar")
@Slf4j
public class DoubleHistogramStrategy extends DefaultStrategy implements ModuConfStrategy {

    @Override
    protected void getKpiInfo(Map<String, String> sqlMap, Map<String, Object> map, List<String> dimenList) {
        log.info("=========getKpiInfo============");
        super.getKpiInfo(sqlMap, map, dimenList);
    }

    @Override
    protected List<String> getDimension(Map<String, Object> map) {
        log.info("=========getDimension===========");
        return super.getDimension(map);
    }
}
