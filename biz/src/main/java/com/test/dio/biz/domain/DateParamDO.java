package com.test.dio.biz.domain;

import lombok.Data;

import java.util.Date;

/**
 * 时间参数
 */
@Data
public class DateParamDO {

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 期度类型
     */
    private String periodType;

    /**
     * 时间模式
     */
    private String pattern;

    /**
     * 时间类型
     */
    private String timeType;

    /**
     * 时间跨度
     */
    private String span;
}
