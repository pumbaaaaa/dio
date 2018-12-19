package com.test.dio.biz.mapper;

import com.test.dio.biz.entity.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

    @Insert(" INSERT INTO T_POST(TOPIC_ID, TITLE, RELIES, USER_ID) " +
            " VALUES(#{topicId}, #{title}, #{replies}, #{userId}) ")
    int insertPost(Post post);
}
