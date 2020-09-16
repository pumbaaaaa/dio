package com.test.dio.biz.consts;

/**
 * 组件常量
 */
public interface ModuConstant {

    /**
     * VALUE
     */
    String VALUE = "value";

    /**
     * 指标配置
     */
    String FORM_VALUE = "formValue";

    /**
     * 指标ID
     */
    String KPI_ID = "kpiId";

    /**
     * 指标SQL_ID
     */
    String SQL_ID = "sqlId";

    /**
     * 筛选条件字段
     */
    String FITR_COND = "fitrCond";

    /**
     * 筛选条件字段值
     */
    String FITR_COND_VAL = "fitrCondVal";

    /**
     * 等于表达式
     */
    String E_EXPRESSION = "%s = %s";

    /**
     * 大于表达式
     */
    String GT_EXPRESSION = "%s > %s";

    /**
     * 小于表达式
     */
    String LT_EXPRESSION = "%s < %s";

    /**
     * 大于表达式
     */
    String GE_EXPRESSION = "%s >= %s";

    /**
     * 大于表达式
     */
    String LE_EXPRESSION = "%s <= %s";

    /**
     * Mybatis IF动态标签
     */
    String IF_SCRIPT = "<if test=\"%s != null and %s != ''\">\nAND %s = #{%s}\n</if>\n";

    /**
     * 小于转义字符
     */
    String LT_ESCAPE = "&lt;";

    /**
     * 小于符号
     */
    String LT_LABEL = "<";

    /**
     * 别名
     */
    String AS = "AS";

    /**
     * 昨天
     */
    String DAY = "DAY";

    /**
     * 近七天
     */
    String DAY7 = "DAY7";

    /**
     * 近三十天
     */
    String DAY30 = "DAY30";

    /**
     * 近半年
     */
    String MONTH6 = "MONTH6";

    /**
     * 近一年
     */
    String YEAR = "YEAR";

    /**
     * 当前年
     */
    String CRTYEAR = "CRTYEAR";

    /**
     * 月份格式化
     */
    String MYSQL_YYYY_MM = "%Y-%m";
    String PG_YYYY_MM = "yyyy-MM";

    /**
     * 日期格式化
     */
    String MYSQL_YYYY_MM_DD = "%Y-%m-%d";
    String PG_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 分钟格式化
     */
    String MYSQL_YYYY_MM_DD_HH_MM_00 = "%Y-%m-%d %";
    String PG_YYYY_MM_DD_HH_MM_00 = "yyyy-MM-dd hh:mm:00";
}
