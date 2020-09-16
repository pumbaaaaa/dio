package com.test.dio.biz.strategy.modu.impl;

import com.test.dio.biz.strategy.modu.ModuConfStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 表单组件类型策略
 *
 * <p>@Component beanName为前端传入参数componentName
 */
@Component("inputTable")
@Slf4j
public class TableStrategy extends DefaultStrategy implements ModuConfStrategy {

}
