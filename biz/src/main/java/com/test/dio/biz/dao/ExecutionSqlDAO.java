package com.test.dio.biz.dao;

import com.test.dio.biz.util.ModuConfProvider;
import com.test.dio.biz.util.SQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ExecutionSqlDAO {

    /**
     * 校验SQL是否可以执行
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    @SelectProvider(type = ModuConfProvider.class, method = "checkSql")
    List<Map<String, Object>> checkSql(SQL sql) throws SQLException;
}
