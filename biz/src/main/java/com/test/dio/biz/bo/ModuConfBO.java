package com.test.dio.biz.bo;

import java.util.List;
import java.util.Map;

public interface ModuConfBO {

    /**
     * 将sql插入kpi_sql_info_a表中
     *
     * @param sqlMap
     */
    void saveKpiSql(Map<String, String> sqlMap);

    /**
     * 将conf序列化为json字段保存到modu_list_a表中
     *
     * @param conf
     */
    void saveModuConf(List<Map<String, Object>> conf);
}
