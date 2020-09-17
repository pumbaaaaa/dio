package com.test.dio.biz.consts;

/**
 * SQL常量
 */
public interface SqlConstant {

    /**
     * 等于表达式
     */
    String E_EXPRESSION = "%s = '%s'";

    /**
     * 大于表达式
     */
    String GT_EXPRESSION = "%s > '%s'";

    /**
     * 小于表达式
     */
    String LT_EXPRESSION = "%s < '%s'";

    /**
     * 大于表达式
     */
    String GE_EXPRESSION = "%s >= '%s'";

    /**
     * 大于表达式
     */
    String LE_EXPRESSION = "%s <= '%s'";

    /**
     * 小于转义字符
     */
    String LT_ESCAPE = "&lt;";

    /**
     * 小于符号
     */
    String LT_LABEL = "<";

    /**
     * 左侧关联表
     */
    String TABLE_LEFT = "tableLeft";

    /**
     * 右侧关联表
     */
    String TABLE_RIGHT = "tableRight";

    /**
     * 补充字段
     */
    String SPLM_COLUMN = "_";

    /**
     * 别名
     */
    String AS = "AS";

    /**
     * PG时间类型
     */
    String PG_STEP_ONE_MON = "'1 MON'";
    String PG_STEP_ONE_DAY = "'1 DAY'";
    String PG_STEP_ONE_HOUR = "'1 HOUR'";

    /**
     * MYSQL时间类型
     */
    String MYSQL_STEP_ONE_MON = "MON";
    String MYSQL_STEP_ONE_DAY = "DAY";
    String MYSQL_STEP_ONE_HOUR = "HOUR";

    /**
     * 日期格式化SQL
     */
    String MYSQL_DATE_FORMAT = "DATE_FORMAT(%s, '%s')";
    String PG_DATE_FORMAT = "TO_CHAR(%s, '%s')";

    /**
     * PG补充日期SQL
     * %s1: 开始时间
     * %s2: 结束时间
     * %s3: 时间类型
     * %s4: 补充时间字段名
     * %s5: 表别名
     * %s6: 原时间字段名
     * %s7: pattern
     * %s8: 补充时间字段名
     * %s9: pattern
     */
    String PG_SPLM_DATE = "(SELECT GENERATE_SERIES(DATE('%s'), DATE('%s'), '%s')::DATE AS %s) AS %s ON TO_CHAR(%s, '%s') = TO_CHAR(%s, '%s')";
    /**
     * MYSQL补充日期SQL
     * %s1: 开始时间
     * %s2: 时间类型
     * %s3: 补充时间字段名
     * %s4: 开始时间与结束时间跨度
     * %s5: 表别名
     * %s6: 原时间字段名
     * %s7: pattern
     * %s8: 补充时间字段名
     * %s9: pattern
     */
    String MYSQL_SPLM_DATE = "(SELECT (DATE('%s') + INTERVAL (step - 1) %s) AS %s FROM \n" +
            "(SELECT CAST(code_val AS signed ) AS step FROM biz_dic_map_a WHERE fld_code = 'biz_kpi_generate_series' ORDER BY step ASC LIMIT %s) t) AS %s \n" +
            "ON DATE_FORMAT(%s, '%s') = DATE_FORMAT(%s, '%s')";

    /**
     * 月份格式化
     */
    String MYSQL_YYYY_MM = "%Y-%m";
    String YYYY_MM = "yyyy-MM";

    /**
     * 日期格式化
     */
    String MYSQL_YYYY_MM_DD = "%Y-%m-%d";
    String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 分钟格式化
     */
    String MYSQL_YYYY_MM_DD_HH_00_00 = "%Y-%m-%d %H:00:00";
    String YYYY_MM_DD_HH_00_00 = "yyyy-MM-dd hh:00:00";

    /**
     * Mybatis IF动态标签
     */
    String IF_SCRIPT = "<if test=\"%s != null and %s != ''\">\nAND %s = #{%s}\n</if>\n";

}
