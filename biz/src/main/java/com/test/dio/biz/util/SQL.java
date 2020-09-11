package com.test.dio.biz.util;

/**
 * 拼接Mybatis可执行Sql类
 *
 * <p> 通过@SelectProvider等注解使用
 */
public class SQL extends AbstractSQL<SQL> {

    /**
     * 前缀
     */
    private static final String PREFIX = "<script>\n";
    /**
     * 后缀
     */
    private static final String SUFFIX = "\n</script>";

    @Override
    public SQL getSelf() {
        return this;
    }

    /**
     * 拼接Mybatis标签可执行Sql
     *
     * @return
     */
    public String getScriptSql() {

        return PREFIX.concat(super.toString()).concat(SUFFIX);
    }
}
