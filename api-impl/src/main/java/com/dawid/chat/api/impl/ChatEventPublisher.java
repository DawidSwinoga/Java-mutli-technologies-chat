package com.dawid.chat.api.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Created by Dawid on 20.11.2018 at 22:56.
 */

@Component
@RequiredArgsConstructor
public class ChatEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(Object event) {
        applicationEventPublisher.publishEvent(event);
    }

}
