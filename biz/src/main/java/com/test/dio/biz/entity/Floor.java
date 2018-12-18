package com.test.dio.biz.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Floor {

    private Long topicId;
    private String userId;
    private String content;
    private String floor;
    private String replyTime;
}
