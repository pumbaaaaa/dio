package com.test.dio.biz.service.impl;

import com.test.dio.biz.bo.ModuConfBO;
import com.test.dio.biz.domain.KpiSqlInfoDTO;
import com.test.dio.biz.domain.ModuKpiParamDTO;
import com.test.dio.biz.service.ModuConfService;
import com.test.dio.biz.factory.ModuConfStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ModuConfServiceImpl implements ModuConfService {


    @Autowired
    private ModuConfStrategyFactory factory;

    @Autowired
    private ModuConfBO moduConfBO;

    @Override
    public void saveModuConf(ModuKpiParamDTO param) {

        // 获取conf
        List<Map<String, Object>> conf = param.getFormConfigData();

        // 根据不同的组件类型数据结构解析生成sql
        List<KpiSqlInfoDTO> kpiSqlList = factory.getStrategy(param.getComponentName()).formValueStructAna(conf);

        // 将sql插入kpi_sql_info_a表中

        // 将conf序列化为json字段保存到modu_list_a表中

    }
}
