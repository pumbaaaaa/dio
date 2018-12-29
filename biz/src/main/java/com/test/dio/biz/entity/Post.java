package com.test.dio.biz.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Post {

    private Long id;
    private Long topicId;
    private String title;
    private String url;
    private Long replies;
    private String userId;
    private Date createTime;
}
