package com.test.dio.biz.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MetaDataDAO {

    @Select("select column_name from information_schema.columns " +
            "where table_schema='public' and table_name=#{tabName} and column_name in (${columns})")
    List<String> selectColumnsByTabName(@Param("columns") String columns,
                                        @Param("tabName") String tabName);
}
