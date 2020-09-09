package com.test.dio.biz.bo.impl;

import com.test.dio.biz.bo.ModuConfBO;
import com.test.dio.biz.dao.ModuConfDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ModuConfBOImpl implements ModuConfBO {

    @Autowired
    private ModuConfDAO moduConfDAO;

    @Override
    public void saveKpiSql(Map<String, String> sqlMap) {
        sqlMap.forEach((k, v) -> {

        });

    }

    @Override
    public void saveModuConf(List<Map<String, Object>> conf) {

    }
}
