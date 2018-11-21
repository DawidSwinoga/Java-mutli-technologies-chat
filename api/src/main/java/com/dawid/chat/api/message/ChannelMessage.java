package com.dawid.chat.api.message;

import com.dawid.chat.api.channel.ChannelIdentifiable;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * Created by Dawid on 17.11.2018 at 20:49.
 */

@EqualsAndHashCode(callSuper = true)
@Value
public class ChannelMessage extends MessageDto implements ChannelIdentifiable, Serializable {
    private final String channelId;

    public ChannelMessage(Instant dateTime, String text, String author, String channelId) {
        super(dateTime, text, author);
        this.channelId = channelId;
    }
}
