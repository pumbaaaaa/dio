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
     * 时间类型模式
     */
    private String periodType;
}
