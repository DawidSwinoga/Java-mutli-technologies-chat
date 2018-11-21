package com.dawid.chat.api.impl.user;

import com.dawid.chat.api.channel.ChannelUserChangeDto;

/**
 * Created by Dawid on 17.11.2018 at 22:57.
 */
public class UserLeaveChatEvent extends ChannelUserChangeDto {
    public UserLeaveChatEvent(String username, String channelId) {
        super(username, channelId);
    }
}
