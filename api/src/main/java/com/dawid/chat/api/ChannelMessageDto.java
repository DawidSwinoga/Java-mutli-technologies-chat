package com.dawid.chat.api;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.Instant;

/**
 * Created by Dawid on 17.11.2018 at 20:49.
 */

@EqualsAndHashCode(callSuper = true)
@Value
public class ChannelMessageDto extends MessageDto {
    private final String channelId;

    public ChannelMessageDto(Instant dateTime, String text, String author, String channelId) {
        super(dateTime, text, author);
        this.channelId = channelId;
    }
}
