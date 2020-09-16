package com.test.dio.biz.strategy.modu.impl;

import com.test.dio.base.exception.AppBusinessException;
import com.test.dio.biz.consts.Constant;
import com.test.dio.biz.consts.ModuConstant;
import com.test.dio.biz.dao.KpiConfDAO;
import com.test.dio.biz.dao.MetaDataDAO;
import com.test.dio.biz.dao.ModuConfDAO;
import com.test.dio.biz.domain.*;
import com.test.dio.biz.factory.SqlStrategyFactory;
import com.test.dio.biz.strategy.modu.ModuConfStrategy;
import com.test.dio.biz.util.CommonUtils;
import com.test.dio.biz.util.DateUtil;
import com.test.dio.biz.util.SQL;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 默认组件
 */
@Component("defaultStrategy")
@Slf4j
public abstract class DefaultStrategy implements ModuConfStrategy {

    @Autowired
    private SqlStrategyFactory sqlStrategyFactory;

    @Autowired
    private KpiConfDAO kpiConfDAO;

    @Autowired
    private ModuConfDAO moduConfDAO;

    @Autowired
    private MetaDataDAO metaDataDAO;

    @Value("#{'${db.date.columns}'.split(',')}")
    private List<String> dateColumns;

    /**
     * 对拥有formValue结构解析，生成配置SQL
     *
     * @param param 传参结构体
     * @return
     */
    @Override
    public final List<KpiSqlInfoDTO> formValueStructAna(ModuKpiParamDTO param) {

        List<KpiSqlInfoDTO> kpiSqlList = new ArrayList<>(10);

        // 获取指标维度
        String dimension = param.getDimen().getValue();

        // 获取指标期度开始结束时间
        DateParamDO dateParam = getPeriod(param.getPeriod());

        // 获取组件联动参数
        List<String> linkageParam = param.getParam().stream().map(ConfDTO::getValue).collect(Collectors.toList());

        // 获取config结构
        List<Map<String, Object>> conf = param.getFormConfigData();

        conf.forEach(map -> {
            // 解析指标配置信息，拼接SELECT指标；FROM表名；WHERE筛选条件
            getKpiInfo(map, kpiSqlList);

            // 遍历所有指标SQL
            kpiSqlList.forEach(kpiSql -> {

                // 获取SQL类
                SQL scriptSQL = kpiSql.getScriptSQL();

                // 根据业务维度，时间维度，时间范围组装SQL
                buildScriptSql(dimension, dateParam, kpiSql, scriptSQL);

                // 校验SQL是否可以执行
                checkSql(kpiSql);

                //校验组件联动参数是否存在指标表中, 如果存在，拼接动态参数
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
        String kpiName = String.valueOf(formValue.get(ModuConstant.VALUE));
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
        scriptSQL.WHERE_IF(String.format(ModuConstant.E_EXPRESSION, fitrCond, fitrCondVal),
                StringUtils.isNotBlank(fitrCond) && StringUtils.isNotBlank(fitrCondVal));

        // 生成sqlId
        String sqlId = CommonUtils.getUUID();

        // 创建KpiSqlInfo，添加到kpiSqlList
        KpiSqlInfoDTO kpiSqlInfo = new KpiSqlInfoDTO();
        kpiSqlInfo.setScriptSQL(scriptSQL);
        kpiSqlInfo.setKpiId(kpiId);
        kpiSqlInfo.setKpiName(kpiName);
        kpiSqlInfo.setSqlId(sqlId);
        kpiSqlInfo.setTabName(kpiInfo.getTabname());
        kpiSqlInfo.setKpiDbId(kpiInfo.getKpiDbId());

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
        return kpiConfDAO.selectKpiInfoById(kpiId);
    }

    /**
     * 获取指标期度，根据维度类型获取开始结束时间，默认为昨日
     *
     * @param param 传参结构体
     */
    private DateParamDO getPeriod(ConfDTO param) {

        String period = param.getValue();

        // 根据维度类型获取开始结束时间，默认为昨日
        DateParamDO result = CommonUtils.getDateByPeriod(period);
        result.setPeriodType(period);
        return result;
    }

    /**
     * 组装SQL
     *
     * @param dimension 维度
     * @param dateParam 时间筛选条件
     * @param kpiSql    指标SQL信息
     * @param scriptSQL SQL类
     */
    private void buildScriptSql(String dimension, DateParamDO dateParam, KpiSqlInfoDTO kpiSql, SQL scriptSQL) {
        // 根据表名查询出时间字段
        String dateColumn = getDateColumn(dateColumns, kpiSql.getTabName());
        String dateFormat = sqlStrategyFactory.getStrategy(kpiSql.getKpiDbId())
                .getDateFormat(dateColumn, dateParam.getPeriodType());

        // 拼接SELECT表达式：业务维度，时间维度
        scriptSQL.SELECT(dimension);
        scriptSQL.SELECT(dateFormat + ModuConstant.AS + dateColumn);

        // 拼接WHERE表达式：时间范围
        String startDate = DateUtil.genSqlWithPattern(dateParam.getStartDate(), Constant.YYYY_MM_DD);
        String endDate = DateUtil.genSqlWithPattern(dateParam.getEndDate(), Constant.YYYY_MM_DD);
        scriptSQL.WHERE(String.format(ModuConstant.GE_EXPRESSION, dateColumn, startDate));
        scriptSQL.WHERE(String.format(ModuConstant.LT_EXPRESSION, dateColumn, endDate));

        // 拼接GROUP BY表达式：业务维度，时间维度
        scriptSQL.GROUP_BY(dimension);
        scriptSQL.GROUP_BY(dateFormat);
    }

    /**
     * 查询表对应时间字段
     *
     * @param tabName     表名
     * @param dateColumns 所有时间字段
     * @return
     */
    private String getDateColumn(List<String> dateColumns, String tabName) {

        List<String> columnList = metaDataDAO.selectColumnsByTabName(CommonUtils.joinCode(dateColumns), tabName);

        return CollectionUtils.isEmpty(columnList) ? null : columnList.stream().findAny().get();
    }

    /**
     * 校验SQL是否可执行
     * 如果SQL无法执行，抛出对应指标配置错误异常
     *
     * @param kpiSql 指标SQL类
     */
    private void checkSql(KpiSqlInfoDTO kpiSql) {
        try {
            moduConfDAO.validProviderSql(kpiSql.getScriptSQL());
        } catch (SQLException e) {
            throw new AppBusinessException(e.getMessage());
        }
    }


    /**
     * 校验表中是否有该参数字段
     * 如果没有，抛出对应指标表中无该参数异常
     * 如果有，拼接参数动态SQL
     *
     * @param linkageParam 组件联动参数集
     * @param kpiSql       指标SQL类
     */
    private void checkParam(List<String> linkageParam, KpiSqlInfoDTO kpiSql) {

        List<String> columnList = metaDataDAO.selectColumnsByTabName(CommonUtils.joinCode(linkageParam), kpiSql.getTabName());

        SQL scriptSQL = kpiSql.getScriptSQL();


        linkageParam.forEach(e -> {
            if (!columnList.contains(e)) {

                // 如果数据库表中没有用户配置的参数字段，抛出异常
                throw new AppBusinessException(kpiSql.getKpiName() + "联动参数配置错误");
            } else {

                // 如果组件联动参数字段没有问题，拼接动态SQL
                scriptSQL.WHERE_IF_SCRIPT(String.format(ModuConstant.IF_SCRIPT, e, e, e, e));
            }
        });


    }

}
