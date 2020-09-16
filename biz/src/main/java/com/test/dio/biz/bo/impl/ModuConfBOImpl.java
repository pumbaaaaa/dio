package com.test.dio.biz.bo.impl;

import com.test.dio.biz.bo.ModuConfBO;
import com.test.dio.biz.dao.ModuConfDAO;
import com.test.dio.biz.domain.KpiSqlInfoDTO;
import com.test.dio.biz.domain.ModuKpiParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ModuConfBOImpl implements ModuConfBO {

    @Autowired
    private ModuConfDAO moduConfDAO;

    @Override
    public void saveKpiSql(List<KpiSqlInfoDTO> kpiSqlList) {

    }

    @Override
    public void saveModuConf(ModuKpiParamDTO param) {

    }
}
