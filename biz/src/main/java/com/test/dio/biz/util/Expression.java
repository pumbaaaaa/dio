package com.test.dio.biz.util;

import java.util.Map;

/**
 * 表达式
 */
public interface Expression {

    /**
     * 生成表达式
     *
     * @param param
     * @return
     */
    String generateExpression(Map<String, Object> param);
}
