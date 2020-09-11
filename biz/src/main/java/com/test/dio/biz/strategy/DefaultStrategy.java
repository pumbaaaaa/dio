package com.test.dio.biz.strategy;

import com.test.dio.base.exception.AppBusinessException;
import com.test.dio.biz.consts.ModuConstant;
import com.test.dio.biz.dao.ModuConfDAO;
import com.test.dio.biz.domain.DateParamDO;
import com.test.dio.biz.domain.KpiInfoDO;
import com.test.dio.biz.domain.KpiSqlInfoDTO;
import com.test.dio.biz.util.CommonUtils;
import com.test.dio.biz.util.SQL;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 默认组件
 */
@Component("defaultStrategy")
@Slf4j
public abstract class DefaultStrategy implements ModuConfStrategy {

    @Autowired
    private ModuConfDAO moduConfDAO;

    @Value("#{'${db.date.columns}'.split(',')}")
    private List<String> dateColumns;

    /**
     * 对拥有formValue结构解析，生成配置SQL
     *
     * @param param 传参结构体
     * @return
     */
    @Override
    public final List<KpiSqlInfoDTO> formValueStructAna(List<Map<String, Object>> param) {

        List<KpiSqlInfoDTO> kpiSqlList = new ArrayList<>(10);

        param.forEach(map -> {
            // 解析指标配置信息，拼接SELECT指标；FROM表名；WHERE筛选条件
            getKpiInfo(map, kpiSqlList);

            // 获取指标维度
            String dimension = getDimension(map);

            // 获取指标期度开始结束时间
            DateParamDO dateParam = getPeriod(map);

            // 获取组件联动参数
            List<String> linkageParam = getLinkageParam(map);

            // 遍历所有指标SQL
            kpiSqlList.forEach(kpiSql -> {

                // 获取SQL类
                SQL scriptSQL = kpiSql.getScriptSQL();

                // 根据表名查询出时间字段
                String dateColumn = getDateColumn(dateColumns, kpiSql.getTabName());

                // 拼接SELECT表达式：业务维度，时间维度
                scriptSQL.SELECT(dimension);
                scriptSQL.SELECT(dateColumn);

                // 拼接WHERE表达式：时间范围
                scriptSQL.WHERE(String.format(ModuConstant.GE_EXPRESSION, dateColumn, dateParam.getStartDate().toString()));
                scriptSQL.WHERE(String.format(ModuConstant.LT_EXPRESSION, dateColumn, dateParam.getEndDate().toString()));

                // 拼接GROUP BY表达式：业务维度，时间维度
                scriptSQL.GROUP_BY(dimension);
                scriptSQL.GROUP_BY(dateColumn);

                // 校验SQL是否可以执行
                checkSql(scriptSQL);

                //校验组件联动参数是否存在指标表中
                checkParam(linkageParam, kpiSql);

            });
        });

        return kpiSqlList;
    }


    /**
     * @param param 传参结构体
     */
    @SuppressWarnings("unchecked")
    protected void getKpiInfo(Map<String, Object> param, List<KpiSqlInfoDTO> kpiSqlList) {

        // 获取formValue
        Object formValueObj = param.get(ModuConstant.FORM_VALUE);

        // 判断非空
        if (Objects.nonNull(formValueObj)) {

            if (formValueObj instanceof Map) {
                // 如果只有一个指标, 生成该指标对应sql
                generateKpiSqlByConf((Map<String, Object>) formValueObj, kpiSqlList);

            } else if (formValueObj instanceof List) {
                // 如果有多个指标
                ((List<Map<String, Object>>) formValueObj).stream()
                        // 通过formValue中是否有kpiId来过滤出是指标配置的Map
                        .filter(formValue -> StringUtils.isNotBlank(String.valueOf(formValue.get(ModuConstant.KPI_ID))))
                        // 遍历生成指标对应sql
                        .forEach(formValue -> generateKpiSqlByConf(formValue, kpiSqlList));
            }
        }

    }

    /**
     * 通过指标配置信息组装SQL, 赋值到formValue及sqlMap中
     *
     * @param formValue  指标配置
     * @param kpiSqlList 指标对应SQL集合
     */
    private void generateKpiSqlByConf(Map<String, Object> formValue,
                                      List<KpiSqlInfoDTO> kpiSqlList) {

        // 获取指标ID
        String kpiId = String.valueOf(formValue.get(ModuConstant.KPI_ID));
        // 获取指标名称
        String kpiName = String.valueOf(formValue.get(ModuConstant.ALIAS_NAME));
        // 获取筛选条件字段
        String fitrCond = String.valueOf(formValue.get(ModuConstant.FITR_COND));
        // 获取筛选条件字段值
        String fitrCondVal = String.valueOf(formValue.get(ModuConstant.FITR_COND_VAL));

        // 通过指标ID获取指标配置信息
        KpiInfoDO kpiInfo = getKpiInfo(kpiId);

        // 拼接sql
        SQL scriptSQL = new SQL();
        scriptSQL.SELECT(kpiInfo.getKpiSql());
        scriptSQL.FROM(kpiInfo.getTabname());
        scriptSQL.WHERE(String.format(ModuConstant.E_EXPRESSION, fitrCond, fitrCondVal));

        // 生成sqlId
        String sqlId = CommonUtils.getUUID();

        // 创建KpiSqlInfo，添加到kpiSqlList
        KpiSqlInfoDTO kpiSqlInfo = new KpiSqlInfoDTO();
        kpiSqlInfo.setScriptSQL(scriptSQL);
        kpiSqlInfo.setKpiId(kpiId);
        kpiSqlInfo.setKpiName(kpiName);
        kpiSqlInfo.setSqlId(sqlId);
        kpiSqlInfo.setTabName(kpiInfo.getTabname());

        // 添加到返回集合中
        kpiSqlList.add(kpiSqlInfo);

        // 将sqlId赋值到formValue中
        formValue.put(ModuConstant.SQL_ID, sqlId);

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
     * 获取指标维度
     *
     * @param param 传参结构体
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getDimension(Map<String, Object> param) {

        String dimension = null;

        // 获取key为维度的Map
        Object dimenObj = param.get(ModuConstant.DIMEN);

        // 校验维度
        if (Objects.nonNull(dimenObj) && dimenObj instanceof Map) {

            dimension = String.valueOf(((Map) dimenObj).get(ModuConstant.VALUE));

        }
        return dimension;
    }

    /**
     * 获取指标期度
     *
     * @param param 传参结构体
     */
    @SuppressWarnings("unchecked")
    private DateParamDO getPeriod(Map<String, Object> param) {

        DateParamDO dateParam = new DateParamDO();

        // 获取key为维度的Map
        Object periodObj = param.get(ModuConstant.PERIOD);

        if (Objects.nonNull(periodObj) && periodObj instanceof Map) {

            String period = String.valueOf(((Map) periodObj).get(ModuConstant.VALUE));

            // 根据维度类型获取开始结束时间，默认为昨日
            dateParam = CommonUtils.getDateByPeriod(period);
        }

        return dateParam;

    }

    /**
     * 获取指标联动参数
     *
     * @param param 传参结构体
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<String> getLinkageParam(Map<String, Object> param) {

        List<String> paramList = new ArrayList<>(10);

        // 获取key为联动参数的Map
        Object paramObj = param.get(ModuConstant.PARAM);

        if (Objects.nonNull(paramObj) && paramObj instanceof List) {
            ((List<Map<String, Object>>) paramObj).forEach(e -> paramList.add(String.valueOf(e.get(ModuConstant.VALUE))));
        }

        return paramList;
    }

    /**
     * 查询表对应时间字段
     *
     * @param tabName     表名
     * @param dateColumns 所有时间字段
     * @return
     */
    private String getDateColumn(List<String> dateColumns, String tabName) {

        return null;
    }


    /**
     * 校验SQL是否可执行
     * 如果SQL无法执行，抛出对应指标配置错误异常
     *
     * @param scriptSQL 执行SQL
     */
    private void checkSql(SQL scriptSQL) {
        try {
            moduConfDAO.validProviderSql(scriptSQL);
        } catch (Exception e) {
            throw new AppBusinessException(e.getMessage());
        }
    }


    /**
     * 校验表中是否有该参数字段
     * 如果没有，抛出对应指标表中无该参数异常
     *
     * @param linkageParam 组件联动参数集
     * @param kpiSql       指标SQL类
     */
    private void checkParam(List<String> linkageParam, KpiSqlInfoDTO kpiSql) {

    }

}
