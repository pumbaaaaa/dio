package com.test.dio.biz.strategy;

import java.util.List;
import java.util.Map;

/**
 * 组件配置策略
 */
public interface ModuConfStrategy {

    /**
     * formValue结构解析
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> formValueStructAna(List<Map<String, Object>> param);
}
