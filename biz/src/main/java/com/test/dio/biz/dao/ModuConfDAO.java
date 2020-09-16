package com.test.dio.biz.dao;

import com.test.dio.biz.util.ModuConfProvider;
import com.test.dio.biz.util.SQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ModuConfDAO {

    @SelectProvider(type = ModuConfProvider.class, method = "validSql")
    List<Map<String, Object>> validProviderSql(SQL sql) throws SQLException;
}
