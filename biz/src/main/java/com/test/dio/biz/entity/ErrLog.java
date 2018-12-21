package com.test.dio.biz.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrLog {

    private Long id;
    private Long lastReplies;
    private String url;
    private String stackTrace;
}
