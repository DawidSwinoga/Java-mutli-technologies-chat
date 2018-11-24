package com.dawid.chat.client;

import com.dawid.chat.commons.ChatEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.Serializable;

/**
 * Created by Dawid on 22.11.2018 at 12:51.
 */

@Slf4j
@Component
@RequiredArgsConstructor
@DependsOn("chatEventPublisher")
public class ChatMessageListener implements MessageListener {
    private final ChatEventPublisher chatEventPublisher;

    @Override
    public void onMessage(Message message) {
        try {
            Serializable object = ((ActiveMQObjectMessage) message).getObject();
            log.info("Receive message: {}", object);
            chatEventPublisher.publishEvent(object);
        } catch (JMSException e) {
            log.error("Queue receive error", e);
        }
    }
}
