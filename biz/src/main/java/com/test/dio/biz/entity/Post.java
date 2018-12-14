package com.test.dio.biz.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Post {

    private String topicId;
    private String userId;
    private String title;
    private String content;
    private Long floor;
    private String replyTime;
}
