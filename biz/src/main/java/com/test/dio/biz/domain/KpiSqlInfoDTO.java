package com.test.dio.biz.domain;

import com.test.dio.biz.util.SQL;
import lombok.Data;

@Data
public class KpiSqlInfoDTO {

    /**
     * 指标ID
     */
    private String kpiId;

    /**
     * 指标名称
     */
    private String kpiName;

    /**
     * SqlID
     */
    private String sqlId;

    /**
     * SQL
     */
    private String sql;

    /**
     * SQL类
     */
    private SQL scriptSQL;

    /**
     * 表名
     */
    private String tabName;

}
