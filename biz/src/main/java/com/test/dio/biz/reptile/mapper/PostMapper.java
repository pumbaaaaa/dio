package com.test.dio.biz.reptile.mapper;

import com.test.dio.biz.reptile.entity.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PostMapper {

    @Insert(" INSERT INTO T_POST(TOPIC_ID, TITLE, REPLIES, URL, USER_ID) " +
            " VALUES(#{topicId}, #{title}, #{replies}, #{url}, #{userId}) ")
    int insertPost(Post post);
}
