package com.test.dio.biz.strategy.modu.impl;

import com.test.dio.biz.domain.DateParamDO;
import com.test.dio.biz.domain.KpiSqlInfoDTO;
import com.test.dio.biz.strategy.modu.ModuConfStrategy;
import com.test.dio.biz.util.SQL;
import org.springframework.stereotype.Component;

/**
 * 柱状图-单轴组件
 */
@Component("sanYiFeiYong_sanyi_barBgLine")
public class SingleHistogramStrategy extends DefaultStrategy implements ModuConfStrategy {

    @Override
    protected String buildScriptSql(String dimension, DateParamDO dateParam, KpiSqlInfoDTO kpiSql, SQL scriptSQL) {
        String dateColumn = super.buildScriptSql(dimension, dateParam, kpiSql, scriptSQL);

        return dateColumn;
    }
}
