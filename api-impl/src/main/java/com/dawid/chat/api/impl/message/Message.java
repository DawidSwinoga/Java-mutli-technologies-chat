package com.dawid.chat.api.impl.message;

import com.dawid.chat.api.impl.user.User;
import lombok.Value;

import java.util.Date;

/**
 * Created by Dawid on 17.11.2018 at 19:19.
 */

@Value
public class Message {
    private final Date dateTime;
    private final String text;
    private final User author;

    public Message(User user, String text) {
        this.dateTime = new Date();
        this.text = text;
        this.author = user;
    }

    public String getUsername() {
        return author.getUsername();
    }
}
