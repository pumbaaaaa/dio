package com.test.dio.biz.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Floor {

    private Long id;
    private Long topicId;
    private String userId;
    private Long floor;
    private String content;
    private String hash;
    private Date replyTime;
}
