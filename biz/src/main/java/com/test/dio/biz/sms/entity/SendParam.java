package com.test.dio.biz.sms.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendParam {

    private String accName;

    private String accPwd;

    private String aimcodes;

    private String content;

    private String schTime;

    private String bizId;

    private String dataType;

    private String msgId;
}
