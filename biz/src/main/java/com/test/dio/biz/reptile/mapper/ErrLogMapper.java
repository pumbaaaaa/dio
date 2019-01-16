package com.test.dio.biz.reptile.mapper;

import com.test.dio.biz.reptile.entity.ErrLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ErrLogMapper {

    @Insert(" INSERT INTO T_ERR_LOG(LAST_REPLIES, URL, STACK_TRACE) " +
            " VALUES(#{lastReplies}, #{url}, #{stackTrace}) ")
    int insertErrLog(ErrLog errLog);
}
