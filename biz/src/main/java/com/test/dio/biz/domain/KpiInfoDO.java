package com.test.dio.biz.domain;

import lombok.Data;

/**
 * 指标信息
 */
@Data
public class KpiInfoDO {

    /**
     * 指标ID
     */
    private String kpiId;

    /**
     * 指标英文名称
     */
    private String kpiNo;

    /**
     * 指标公式
     */
    private String kpiSql;

    /**
     * 指标对应表名
     */
    private String tabname;

    /**
     * 指标对应数据库
     */
    private String kpiDbId;

}
