package com.test.dio.biz.util;

/**
 * 拼接Mybatis可执行Sql类
 *
 * <p> 通过@SelectProvider等注解使用
 */
public class SQL extends AbstractSQL<SQL> {

    @Override
    public SQL getSelf() {
        return this;
    }
}
