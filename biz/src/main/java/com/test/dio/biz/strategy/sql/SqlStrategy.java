package com.test.dio.biz.strategy.sql;

import com.test.dio.biz.domain.DateParamDO;

public interface SqlStrategy {

    /**
     * 根据期度类型生成SQL格式化函数
     *
     * @param column 列名
     * @param param  时间参数
     * @return
     */
    String getDateFormat(String column, DateParamDO param);

    /**
     * 生成补充日期数据SQL
     *
     * @param leftColumn  左表连接字段
     * @param rightColumn 右表连接字段
     * @param param       时间参数
     * @return
     */
    String getSupplementSql(String leftColumn, String rightColumn, DateParamDO param);
}
