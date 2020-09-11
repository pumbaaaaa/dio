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
     * 指标维度
     */
    String DIMEN = "dimen";

    /**
     * 指标期度
     */
    String PERIOD = "period";

    /**
     * 指标参数
     */
    String PARAM = "param";

    /**
     * 指标别名
     */
    String ALIAS_NAME = "aliasName";

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
}
