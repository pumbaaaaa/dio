package com.test.dio.biz.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    @Autowired(required = false)
    private SimpMessagingTemplate messagingTemplate;

    public void handleMessage(String message) {
        messagingTemplate.convertAndSendToUser("jojo", "/destination", message);
    }
}
