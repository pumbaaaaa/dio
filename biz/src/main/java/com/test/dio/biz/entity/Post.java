package com.test.dio.biz.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Post {

    private Long topicId;
    private String title;
    private Long relies;
    private Long userId;
}
