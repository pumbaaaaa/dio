package com.test.dio.biz.service.impl;

import com.test.dio.biz.bo.ModuConfBO;
import com.test.dio.biz.domain.KpiSqlInfoDTO;
import com.test.dio.biz.domain.ModuKpiParamDTO;
import com.test.dio.biz.factory.ModuConfStrategyFactory;
import com.test.dio.biz.service.ModuConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuConfServiceImpl implements ModuConfService {


    @Autowired
    private ModuConfStrategyFactory factory;

    @Autowired
    private ModuConfBO moduConfBO;

    @Override
    public void saveModuConf(ModuKpiParamDTO param) {

        // 根据不同的组件类型数据结构解析生成sql
        List<KpiSqlInfoDTO> kpiSqlList = factory.getStrategy(param.getComponentName()).formValueStructAna(param);

        // 将sql插入kpi_sql_info_a表中
        moduConfBO.saveKpiSql(kpiSqlList);

        // 将param序列化后保存到modu_list_a表中
        moduConfBO.saveModuConf(param);
    }
}
