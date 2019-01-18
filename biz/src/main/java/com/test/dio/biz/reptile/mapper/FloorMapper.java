package com.test.dio.biz.reptile.mapper;

import com.test.dio.biz.reptile.entity.Floor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FloorMapper {

    @Select(" SELECT ID, TOPIC_ID, USER_ID, FLOOR, CONTENT, REPLY_TIME " +
            " FROM T_FLOOR " +
            " WHERE TOPIC_ID = #{topicId} ")
    List<Floor> queryFloorByTopicId(Long topicId);

    @Insert(" INSERT INTO T_FLOOR (TOPIC_ID, USER_ID, FLOOR, CONTENT, URL, HASH, REPLY_TIME) " +
            " VALUES(#{topicId}, #{userId}, #{floor}, #{content}, #{url}, #{hash}, #{replyTime}) ")
    int insertFloor(Floor floor);
}
