package com.dawid.chat.api.impl;

import com.dawid.chat.api.channel.ChannelUserChangeDto;

/**
 * Created by Dawid on 17.11.2018 at 22:56.
 */
public class UserJoinToChatEvent extends ChannelUserChangeDto {
    public UserJoinToChatEvent(String username, String channelId) {
        super(username, channelId);
    }
}
