package com.test.dio.biz.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Floor {

    private Long topicId;
    private Long userId;
    private Long floor;
    private String content;
    private String hash;
    private Date replyTime;
}
