package com.test.dio.biz.strategy.modu.impl;

import com.test.dio.biz.consts.SqlConstant;
import com.test.dio.biz.domain.DateParamDO;
import com.test.dio.biz.domain.KpiSqlInfoDTO;
import com.test.dio.biz.factory.SqlStrategyFactory;
import com.test.dio.biz.strategy.modu.ModuConfStrategy;
import com.test.dio.biz.strategy.sql.SqlStrategy;
import com.test.dio.biz.util.SQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 柱状图-双轴组件
 */
@Component("fundLineBar")
@Slf4j
public class DoubleHistogramStrategy extends DefaultStrategy implements ModuConfStrategy {

    @Autowired
    private SqlStrategyFactory sqlStrategyFactory;

    @Override
    protected String buildScriptSql(String dimension, DateParamDO dateParam, KpiSqlInfoDTO kpiSql, SQL scriptSQL) {

        // 获取指标表日期字段
        String dateColumn = super.buildScriptSql(dimension, dateParam, kpiSql, scriptSQL);

        SqlStrategy sqlStrategy = sqlStrategyFactory.getStrategy(kpiSql.getKpiDbId());

        // 补充日期字段
        String splmDateColumn = SqlConstant.SPLM_COLUMN + dateColumn;

        // 根据期度类型生成SQL格式化函数
        String dateFormat = sqlStrategy.getDateFormat(splmDateColumn, dateParam);
        String rightJoin = sqlStrategy.getSupplementSql(dateColumn, splmDateColumn, dateParam);

        // 拼接SELECT表达式：时间维度
        scriptSQL.SELECT(dateFormat + SqlConstant.AS + dateColumn);

        // 拼接RIGHT JOIN 补充日期SQL
        scriptSQL.RIGHT_OUTER_JOIN(rightJoin);

        // 生成开始时间结束时间SQL，拼接WHERE条件
        assemblyWhereExpression(dateParam, scriptSQL, dateColumn);

        // 拼接GROUP BY表达式：时间维度
        scriptSQL.GROUP_BY(dateFormat);


        return dateColumn;
    }
}
