package com.dawid.chat.api.impl.channel;

import com.dawid.chat.api.channel.ChannelInfo;
import lombok.EqualsAndHashCode;

/**
 * Created by Dawid on 18.11.2018 at 00:12.
 */

@EqualsAndHashCode(callSuper = true)
public class ChannelAddedEvent extends ChannelInfo {
    public ChannelAddedEvent(String channelId, String name) {
        super(channelId, name);
    }
}
