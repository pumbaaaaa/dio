package com.test.dio.biz.strategy;

import java.util.List;
import java.util.Map;

/**
 * 组件配置策略
 */
public interface ModuConfStrategy {

    /**
     * formValue结构解析并生成sql返回
     *
     * @param param
     * @return
     */
    Map<String, String> formValueStructAna(List<Map<String, Object>> param);
}
