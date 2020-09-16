package com.test.dio.biz.dao;

import com.test.dio.biz.domain.KpiInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface KpiConfDAO {


    @Select("SELECT kpi_id, kpi_no, kpi_sql, tabname, kpi_db_id" +
            "FROM kpi_info_a" +
            "WHERE kpi_id = #{kpiId}")
    KpiInfoDO selectKpiInfoById(@Param("kpiId") String kpiId);
}
