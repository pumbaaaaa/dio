package com.test.dio.biz.bo;

import com.test.dio.biz.domain.KpiSqlInfoDTO;
import com.test.dio.biz.domain.ModuKpiParamDTO;

import java.util.List;
import java.util.Map;

public interface ModuConfBO {

    /**
     * 将sql插入kpi_sql_info_a表中
     *
     * @param kpiSqlList
     */
    void saveKpiSql(List<KpiSqlInfoDTO> kpiSqlList);

    /**
     * 将param序列化后保存到modu_list_a表中
     *
     * @param param
     */
    void saveModuConf(ModuKpiParamDTO param);
}
