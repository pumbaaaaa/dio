package com.test.dio.biz.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;


@Slf4j
public class CommonUtils {

    /**
     * sql拼接字符
     */
    private static final String S_NULL = "null";
    private static final String S_SINGLE_QUOTES = "'";
    private static final String S_SEPARATOR = "', '";
    private static final int PERCENT = 100;


    public static String joinCode(Collection<?> collection) {
        return collection.isEmpty() ?
                S_NULL : S_SINGLE_QUOTES
                .concat(StringUtils.join(collection, S_SEPARATOR))
                .concat(S_SINGLE_QUOTES);
    }

    /**
     * 计算百分比
     *
     * @param molecular   分子
     * @param denominator 分母
     * @return
     */
    public static BigDecimal calcPercentage(BigDecimal molecular, BigDecimal denominator) {
        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return molecular.multiply(BigDecimal.valueOf(PERCENT)).divide(denominator, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算百分比，不乘100
     *
     * @param molecular   分子
     * @param denominator 分母
     * @return
     */
    public static BigDecimal calcDivide(BigDecimal molecular, BigDecimal denominator) {
        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return molecular.divide(denominator, 2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
