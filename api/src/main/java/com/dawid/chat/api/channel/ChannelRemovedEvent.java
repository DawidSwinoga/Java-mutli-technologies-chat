package com.dawid.chat.api.channel;

import lombok.Value;

import java.io.Serializable;

/**
 * Created by Dawid on 17.11.2018 at 22:59.
 */
@Value
public class ChannelRemovedEvent implements Serializable {
    private final ChannelInfo channelInfo;

    public String getChannelId() {
        return channelInfo.getChannelId();
    }
}
