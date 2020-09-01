package com.test.dio.biz.sms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dio.biz.sms.entity.SendParam;
import com.test.dio.biz.sms.entity.SendResult;
import com.test.dio.biz.util.MD5;
import okhttp3.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SMSService {

    @Autowired
    private OkHttpClient okHttpClient;

    public Boolean sendSms() throws IOException {

        String verifyCode = RandomStringUtils.randomNumeric(6);
        FormBody formBody = new FormBody.Builder()
                .add("accName", "15026837646")
                .add("accPwd", MD5.getMd5String("277655"))
                .add("aimcodes", "17717364882")
                .add("content", String.format("您当前正在查阅个人电子病例档案，验证码：%s，请妥善保管，切勿泄露，不要随意告诉他人，验证码当天有效。【湖南卫健】", verifyCode))
                .add("dataType", "json")
                .build();

        Request request = new Request.Builder()
                .url("http://101.200.141.210/sdk/send")
                .post(formBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();


        if (response.isSuccessful()) {
            String res = response.body().string();
            if (StringUtils.isNotBlank(res)) {
                ObjectMapper objectMapper = new ObjectMapper();
                SendResult sendResult = objectMapper.readValue(res, SendResult.class);
                System.out.println(sendResult);

                return sendResult.getReplyCode() == 1;

            }
        }

        return Boolean.FALSE;
    }
}
