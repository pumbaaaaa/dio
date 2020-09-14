package com.test.dio.biz.strategy;

import com.test.dio.biz.domain.KpiSqlInfoDTO;
import com.test.dio.biz.domain.ModuKpiParamDTO;

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
    List<KpiSqlInfoDTO> formValueStructAna(ModuKpiParamDTO param);
}
