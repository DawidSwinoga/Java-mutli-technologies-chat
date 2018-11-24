package com.dawid.chat.api.message;

import com.dawid.chat.api.channel.ChannelIdentifiable;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dawid on 20.11.2018 at 23:03.
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class MessageEvent extends MessageDto implements ChannelIdentifiable, Serializable {
    private final String channelId;

    public MessageEvent(Date dateTime, String text, String author, String channelId) {
        super(dateTime, text, author);
        this.channelId = channelId;
    }
}