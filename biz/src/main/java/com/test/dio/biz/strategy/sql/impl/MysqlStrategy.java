package com.test.dio.biz.strategy.sql.impl;

import com.test.dio.biz.consts.ModuConstant;
import com.test.dio.biz.strategy.sql.SqlStrategy;
import org.springframework.stereotype.Component;

@Component("MYSQL")
public class MysqlStrategy implements SqlStrategy {

    private static final String DATE_FORMAT = "TO_CHAR('%s', %s)";

    @Override
    public String getDateFormat(String column, String periodType) {
        String pattern;
        if (ModuConstant.CRTYEAR.equalsIgnoreCase(periodType) ||
                ModuConstant.MONTH6.equalsIgnoreCase(periodType) ||
                ModuConstant.YEAR.equalsIgnoreCase(periodType)) {
            pattern = ModuConstant.MYSQL_YYYY_MM;
        } else if (ModuConstant.DAY7.equalsIgnoreCase(periodType) ||
                ModuConstant.DAY30.equalsIgnoreCase(periodType) ||
                ModuConstant.DAY.equalsIgnoreCase(periodType)) {
            pattern = ModuConstant.MYSQL_YYYY_MM_DD;
        } else {
            pattern = ModuConstant.MYSQL_YYYY_MM_DD_HH_MM_00;
        }
        return String.format(DATE_FORMAT, column, pattern);
    }
}
