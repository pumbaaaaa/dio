package com.test.dio.biz.util;


import com.test.dio.biz.consts.Constant;
import com.test.dio.biz.consts.ModuConstant;
import com.test.dio.biz.domain.DateParamDO;
import com.test.dio.biz.domain.DateType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;


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

    /**
     * 获取32位主键
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 根据期度类型返回开始结束时间，以及时间类型模式
     *
     * @param periodType 期度类型
     * @return
     */
    public static DateParamDO getDateByPeriod(String periodType) {
        DateParamDO dateParam = new DateParamDO();
        switch (periodType) {
            case ModuConstant.CRTYEAR:
                LocalDate firstDayOfYear = DateUtil.getFirstDayOfYear();
                dateParam.setStartDate(firstDayOfYear.toDate());
                dateParam.setEndDate(LocalDate.now().toDate());
                break;
            case ModuConstant.DAY7:
                dateParam = DateUtil.getDateInterval(7, DateType.DAY, DateType.DAY);
                break;
            case ModuConstant.DAY30:
                dateParam = DateUtil.getDateInterval(30, DateType.DAY, DateType.DAY);
                break;
            case ModuConstant.MONTH6:
                dateParam = DateUtil.getDateInterval(6, DateType.MONTH, DateType.MONTH);
                break;
            case ModuConstant.YEAR:
                dateParam = DateUtil.getDateInterval(12, DateType.MONTH, DateType.MONTH);
                break;
            default:
                dateParam = DateUtil.getDateInterval(1, DateType.DAY, DateType.DAY);
                break;
        }
        return dateParam;
    }

}
