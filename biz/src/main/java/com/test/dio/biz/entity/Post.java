package com.test.dio.biz.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Post {

    private String userId;
    private String title;
    private String content;
    private Long floor;
    private Date replyTime;
}
