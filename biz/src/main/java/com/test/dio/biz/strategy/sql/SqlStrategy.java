package com.test.dio.biz.strategy.sql;

public interface SqlStrategy {

    /**
     * 根据pattern生成SQL格式化函数
     *
     * @param column  列名
     * @param pattern 时间模式
     * @return
     */
    String getDateFormat(String column, String pattern);
}
