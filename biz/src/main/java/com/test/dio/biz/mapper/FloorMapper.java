package com.test.dio.biz.mapper;

import com.test.dio.biz.entity.Floor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FloorMapper {

    @Insert(" INSERT INTO T_FLOOR (TOPIC_ID, USER_ID, FLOOR, CONTENT, HASH, REPLY_TIME) " +
            " VALUES(#{topicId}, #{userId}, #{floor}, #{content}, #{hash}, #{replyTime}) " +
            " ON CONFLICT (HASH) DO NOTHING ")
    int insertFloor(Floor floor);
}
