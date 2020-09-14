package com.test.dio.biz.factory;

import com.test.dio.biz.strategy.ModuConfStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组件策略工厂
 */
@Component
public class ModuConfStrategyFactory {

    /**
     * 默认组件策略
     */
    private static final String DEFAULT_STRATEGY = "defaultStrategy";

    /**
     * 通过Spring注入所有注册的ModuConfStrategy实现类，Key为BeanName
     */
    @Autowired
    private Map<String, ModuConfStrategy> confStrategy = new ConcurrentHashMap<>(16);


    /**
     * 根据前端传入moduType获取对应组件策略
     * 如果没有对应组件策略，使用默认组件策略
     *
     * @param moduType 组件类型
     * @return
     */
    public ModuConfStrategy getStrategy(String moduType) {
        ModuConfStrategy moduConfStrategy = confStrategy.get(moduType);

        if (Objects.isNull(moduConfStrategy)) {
            return confStrategy.get(DEFAULT_STRATEGY);
        }
        return moduConfStrategy;
    }
}
