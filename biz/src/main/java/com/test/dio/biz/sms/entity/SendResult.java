package com.test.dio.biz.sms.entity;

import lombok.Data;

@Data
public class SendResult {

    private Integer replyCode;

    private String replyMsg;

    private Integer succeedNum;

    private Integer failedNum;

    private Integer chargCount;

    private Integer deduction;

    private Integer balance;
}
