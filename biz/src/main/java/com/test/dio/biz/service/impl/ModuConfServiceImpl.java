package com.test.dio.biz.service.impl;

import com.test.dio.biz.domain.ModuKpiParamDTO;
import com.test.dio.biz.service.ModuConfService;
import com.test.dio.biz.strategy.ModuConfStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ModuConfServiceImpl implements ModuConfService {

    private static final String FORM_VALUE = "formValue";

    @Override
    @SuppressWarnings("unchecked")
    public String saveModuConf(ModuKpiParamDTO param) {

        // 获取conf
        List<Map<String, Object>> conf = param.getFormConfigData();

        conf.stream()
                // 查询到包含formValue结构的Map
                .filter(e -> e.containsKey(FORM_VALUE))
                //
                .forEach(e -> {
                    Object formValue = e.get(FORM_VALUE);
                    if (formValue instanceof Map) {
                        ((Map) formValue).put("sqlId", "aaaa");
                    } else if (formValue instanceof List) {

                    }

                });

        System.out.println(conf);

        return null;
    }
}
