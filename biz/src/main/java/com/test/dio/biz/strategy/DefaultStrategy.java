package com.test.dio.biz.strategy;

import com.test.dio.biz.consts.ModuConstant;
import com.test.dio.biz.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 默认组件
 */
@Component("defaultStrategy")
@Slf4j
public class DefaultStrategy implements ModuConfStrategy {

    @Override
    public Map<String, String> formValueStructAna(List<Map<String, Object>> param) {

        // 初始化返回结果，K为kpiId，V为组装sql
        Map<String, String> sqlMap = new HashMap<>(16);

        param.stream()
                // 过滤出所有包含指标配置信息的Map
                .filter(e -> e.containsKey(ModuConstant.FORM_VALUE))
                .forEach(e -> {

                    // 获取指标维度
                    String dimension = getDimension(e);

                    // 获取指标配置信息, 将生成sql赋值到formValue及sqlMap中


                });

        return sqlMap;
    }

    /**
     * 获取指标维度
     *
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getDimension(Map<String, Object> map) {

        // 初始化维度集合
        List<String> dimensionList = new ArrayList<>(10);

        // 获取key为维度的Map
        Object dimenObj = map.get(ModuConstant.DIMEN);

        // 判断维度不为空
        if (Objects.nonNull(dimenObj)) {

            if (dimenObj instanceof Map) {
                // 如果只有一个维度，添加到维度列表中
                dimensionList.add((String) ((Map) dimenObj).get(ModuConstant.VALUE));
            } else if (dimenObj instanceof List) {
                // 如果有多个维度，遍历添加到维度列表中
                ((List<Map<String, Object>>) dimenObj).forEach(dim -> dimensionList.add((String) dim.get(ModuConstant.VALUE)));
            }
        }

        // 拼接为字符串返回
        return CommonUtils.joinCode(dimensionList);

    }
}
