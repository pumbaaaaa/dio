package com.test.dio.biz.bo.impl;

import com.test.dio.biz.bo.ModuConfBO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ModuConfBOImpl implements ModuConfBO {

    @Override
    public void saveKpiSql(Map<String, String> sqlMap) {
        sqlMap.forEach((k, v) -> {

        });
    }

    @Override
    public void saveModuConf(List<Map<String, Object>> conf) {

    }
}
