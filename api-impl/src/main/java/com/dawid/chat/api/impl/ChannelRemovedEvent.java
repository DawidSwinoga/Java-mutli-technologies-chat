package com.dawid.chat.api.impl;

import com.dawid.chat.api.channel.ChannelInfo;
import lombok.Value;

/**
 * Created by Dawid on 17.11.2018 at 22:59.
 */
@Value
public class ChannelRemovedEvent {
    private final ChannelInfo channelInfo;
}
