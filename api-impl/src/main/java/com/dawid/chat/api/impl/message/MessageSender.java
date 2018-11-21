package com.dawid.chat.api.impl.message;

/**
 * Created by Dawid on 20.11.2018 at 22:09.
 */
public interface MessageSender {
    void sendMessage(String destination, Object message);
}
