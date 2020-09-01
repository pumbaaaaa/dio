package com.test.dio.biz.strategy;

import java.util.List;
import java.util.Map;

public class ModuConfContext {

    private ModuConfStrategy moduConfStrategy;

    public ModuConfContext(ModuConfStrategy moduConfStrategy) {
        this.moduConfStrategy = moduConfStrategy;
    }

    public List<Map<String, Object>> formValueStructAna(List<Map<String, Object>> param) {
        return moduConfStrategy.formValueStructAna(param);
    }
}
