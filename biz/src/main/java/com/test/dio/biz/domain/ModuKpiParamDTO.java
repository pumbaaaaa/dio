package com.test.dio.biz.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 组件指标参数
 */
@Data
public class ModuKpiParamDTO {

    @ApiModelProperty("组件名称")
    private String componentName;

    @ApiModelProperty("指标源ID")
    private String kpiSourceId;

    @ApiModelProperty("组件模块配置")
    private List<Map<String, Object>> formConfigData;

    @ApiModelProperty("组件模块ID")
    private String moduId;
}
