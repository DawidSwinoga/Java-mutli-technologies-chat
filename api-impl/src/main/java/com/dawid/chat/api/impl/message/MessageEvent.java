package com.dawid.chat.api.impl.message;

import com.dawid.chat.api.message.ChannelMessage;
import lombok.Value;

/**
 * Created by Dawid on 20.11.2018 at 23:03.
 */
@Value
public class MessageEvent {
    private final ChannelMessage channelMessage;
}
