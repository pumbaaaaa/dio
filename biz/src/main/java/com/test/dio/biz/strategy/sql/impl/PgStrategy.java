package com.test.dio.biz.strategy.sql.impl;

import com.test.dio.biz.consts.ModuConstant;
import com.test.dio.biz.strategy.sql.SqlStrategy;
import org.springframework.stereotype.Component;

@Component("POSTGRESQL")
public class PgStrategy implements SqlStrategy {

    private static final String DATE_FORMAT = "DATE_FORMAT('%s', %s)";

    @Override
    public String getDateFormat(String column, String periodType) {
        String pattern;
        if (ModuConstant.CRTYEAR.equalsIgnoreCase(periodType) ||
                ModuConstant.MONTH6.equalsIgnoreCase(periodType) ||
                ModuConstant.YEAR.equalsIgnoreCase(periodType)) {
            pattern = ModuConstant.PG_YYYY_MM;
        } else if (ModuConstant.DAY7.equalsIgnoreCase(periodType) ||
                ModuConstant.DAY30.equalsIgnoreCase(periodType) ||
                ModuConstant.DAY.equalsIgnoreCase(periodType)) {
            pattern = ModuConstant.PG_YYYY_MM_DD;
        } else {
            pattern = ModuConstant.PG_YYYY_MM_DD_HH_MM_00;
        }
        return String.format(DATE_FORMAT, column, pattern);
    }
}
