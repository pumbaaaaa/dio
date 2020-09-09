package com.test.dio.biz.strategy;

import com.test.dio.biz.consts.ModuConstant;
import com.test.dio.biz.domain.KpiInfoDO;
import com.test.dio.biz.util.CommonUtils;
import com.test.dio.biz.util.ScriptSQL;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 默认组件
 */
@Component("defaultStrategy")
@Slf4j
public abstract class DefaultStrategy implements ModuConfStrategy {

    /**
     * 对拥有formValue结构解析，生成配置SQL
     *
     * @param param
     * @return
     */
    @Override
    public final Map<String, String> formValueStructAna(List<Map<String, Object>> param) {

        // 初始化返回结果，K为kpiId，V为组装sql
        Map<String, String> sqlMap = new HashMap<>(16);

        // 获取指标维度
        List<String> dimenLists = getDimension(param);

        param.stream()
                // 过滤出所有包含指标配置信息的Map
                .filter(e -> e.containsKey(ModuConstant.FORM_VALUE))
                .forEach(map -> {

                    // 获取指标配置信息
                    getKpiInfo(sqlMap, map, dimenLists);

                });

        return sqlMap;
    }

    /**
     * @param sqlMap    K为kpiId，V为组装sql
     * @param map       解析结构
     * @param dimenList 指标维度
     */
    @SuppressWarnings("unchecked")
    protected void getKpiInfo(Map<String, String> sqlMap, Map<String, Object> map, List<String> dimenList) {

        // 获取formValue
        Object formValueObj = map.get(ModuConstant.FORM_VALUE);

        // 判断非空
        if (Objects.nonNull(formValueObj)) {

            if (formValueObj instanceof Map) {
                // 如果只有一个指标, 生成该指标对应sql
                generateKpiSqlByConf((Map<String, Object>) formValueObj, dimenList, sqlMap);

            } else if (formValueObj instanceof List) {
                // 如果有多个指标, 过滤出是指标配置的Map，遍历生成指标对应sql
                ((List<Map<String, Object>>) formValueObj).stream()
                        .filter(formValue -> StringUtils.isNotBlank((String) formValue.get(ModuConstant.KPI_ID)))
                        .forEach(formValue -> generateKpiSqlByConf(formValue, dimenList, sqlMap));
            }
        }
    }

    /**
     * 通过指标配置信息组装SQL, 赋值到formValue及sqlMap中
     *
     * @param formValue 指标配置
     * @param dimenList 指标维度
     * @param sqlMap    指标SQL及对应指标ID
     */
    private void generateKpiSqlByConf(Map<String, Object> formValue,
                                      List<String> dimenList,
                                      Map<String, String> sqlMap) {

        // 获取指标ID字段
        String kpiId = (String) formValue.get(ModuConstant.KPI_ID);

        // 通过指标ID获取指标配置信息
        KpiInfoDO kpiInfo = getKpiInfo(kpiId);

        // 拼接sql
        ScriptSQL scriptSQL = new ScriptSQL();
        scriptSQL.SELECT();
        scriptSQL.FROM(kpiInfo.getTabname());
        scriptSQL.WHERE();
        scriptSQL.GROUP_BY(CommonUtils.joinCode(dimenList));
        String sql = scriptSQL.toString();

        // 将sql赋值到formValue中
        formValue.put(ModuConstant.KPI_SQL, sql);

        // 将sql赋值到Map中
        sqlMap.put(kpiId, sql);
    }


    /**
     * 通过指标ID获取指标配置信息
     *
     * @param kpiId 指标ID
     */
    private KpiInfoDO getKpiInfo(String kpiId) {

        // TODO 通过缓存查询指标配置信息

        return new KpiInfoDO();
    }

    /**
     * 获取指标维度、期度
     *
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    protected List<String> getDimension(List<Map<String, Object>> param) {

        // 初始化维度集合
        List<String> dimensionList = new ArrayList<>(10);

        // 获取key为维度的Map
        param.forEach(map -> {
            Object dimenObj = map.get(ModuConstant.DIMEN);
            Object periodObj = map.get(ModuConstant.PERIOD);

            // 校验维度
            if (Objects.nonNull(dimenObj) && dimenObj instanceof Map) {

                // 添加到维度列表中
                dimensionList.add((String) ((Map) dimenObj).get(ModuConstant.VALUE));

            }

            // 校验期度
            if (Objects.nonNull(periodObj) && periodObj instanceof Map) {

                // 添加到维度列表中
                dimensionList.add((String) ((Map) periodObj).get(ModuConstant.VALUE));

            }

        });

        return dimensionList;

    }
}
