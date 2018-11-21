package com.dawid.chat.api.impl.message;

import com.dawid.chat.api.impl.user.User;
import lombok.Value;

import java.time.Instant;

/**
 * Created by Dawid on 17.11.2018 at 19:19.
 */

@Value
public class Message {
    private final Instant dateTime;
    private final String text;
    private final User author;

    public Message(User user, String text) {
        this.dateTime = Instant.now();
        this.text = text;
        this.author = user;
    }
}
