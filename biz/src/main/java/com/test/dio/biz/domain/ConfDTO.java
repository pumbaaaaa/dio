package com.test.dio.biz.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 配置参数类
 */
@Data
public class ConfDTO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("值")
    private String value;
}
