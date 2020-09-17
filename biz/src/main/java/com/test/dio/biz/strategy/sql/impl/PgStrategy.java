package com.test.dio.biz.strategy.sql.impl;

import com.test.dio.biz.consts.ModuConstant;
import com.test.dio.biz.consts.SqlConstant;
import com.test.dio.biz.domain.DateParamDO;
import com.test.dio.biz.strategy.sql.SqlStrategy;
import com.test.dio.biz.util.DateUtil;
import org.springframework.stereotype.Component;

@Component("POSTGRESQL")
public class PgStrategy implements SqlStrategy {

    @Override
    public String getDateFormat(String column, DateParamDO param) {
        setDateParamByType(param);

        // 返回格式化SQL
        return String.format(SqlConstant.PG_DATE_FORMAT, column, param.getPattern());
    }

    @Override
    public String getSupplementSql(String leftColumn, String rightColumn, DateParamDO param) {
        setDateParamByType(param);

        // 返回格式化SQL
        return String.format(SqlConstant.PG_SPLM_DATE,
                // 开始时间
                DateUtil.genStrWithPattern(param.getStartDate(), param.getPattern()),
                // 结束时间
                param.getEndDate(),
                // 时间类型
                param.getTimeType(),
                // 补充时间字段名
                rightColumn,
                // 表别名
                SqlConstant.TABLE_RIGHT,
                // 原时间字段名
                leftColumn,
                // pattern
                param.getPattern(),
                // 补充时间字段名
                rightColumn,
                // pattern
                param.getPattern());
    }

    /**
     * 根据期度类型设置时间参数
     *
     * @param param
     */
    private void setDateParamByType(DateParamDO param) {
        String periodType = param.getPeriodType();
        if (ModuConstant.CRTYEAR.equalsIgnoreCase(periodType) ||
                ModuConstant.MONTH6.equalsIgnoreCase(periodType) ||
                ModuConstant.YEAR.equalsIgnoreCase(periodType)) {
            param.setPattern(SqlConstant.YYYY_MM);
            param.setTimeType(SqlConstant.PG_STEP_ONE_MON);
        } else if (ModuConstant.DAY7.equalsIgnoreCase(periodType) ||
                ModuConstant.DAY30.equalsIgnoreCase(periodType) ||
                ModuConstant.DAY.equalsIgnoreCase(periodType)) {
            param.setPattern(SqlConstant.YYYY_MM_DD);
            param.setTimeType(SqlConstant.PG_STEP_ONE_DAY);
        } else {
            param.setPattern(SqlConstant.YYYY_MM_DD_HH_00_00);
            param.setTimeType(SqlConstant.PG_STEP_ONE_HOUR);
        }
    }
}
