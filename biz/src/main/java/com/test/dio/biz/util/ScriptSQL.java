package com.test.dio.biz.util;

/**
 * 拼接Mybatis可执行Sql类
 *
 */
public class ScriptSQL extends AbstractSQL<ScriptSQL> {

    /**
     * 前缀
     */
    private static final String PREFIX = "<script>\n";
    /**
     * 后缀
     */
    private static final String SUFFIX = "\n</script>";

    @Override
    public ScriptSQL getSelf() {
        return this;
    }

    @Override
    public String toString() {
        return PREFIX.concat(super.toString()).concat(SUFFIX);
    }
}
