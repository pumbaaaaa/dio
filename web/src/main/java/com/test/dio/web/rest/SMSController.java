package com.test.dio.web.rest;

import com.test.dio.biz.sms.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/sms")
public class SMSController {

    @Autowired
    private SMSService smsService;

    @GetMapping("/send")
    public ResponseEntity sendSms() throws IOException {

        return ResponseEntity.ok(smsService.sendSms());
    }
}
