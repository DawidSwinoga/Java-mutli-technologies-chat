package com.dawid;

import com.dawid.chat.api.impl.message.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Dawid on 20.11.2018 at 22:10.
 */

@Component
@RequiredArgsConstructor
public class JmsMessageSender implements MessageSender {
    private final JmsTemplate jmsTemplate;

    @Async
    @Override
    public void sendMessage(String destination, Object message) {
        jmsTemplate.convertAndSend(destination, message);
    }
}
