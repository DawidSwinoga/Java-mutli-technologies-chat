package com.dawid.chat.api.user;

import com.dawid.chat.api.channel.ChannelIdentifiable;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * Created by Dawid on 17.11.2018 at 22:57.
 */

@EqualsAndHashCode(callSuper = true)
@Value
public class UserLeaveChatEvent extends UserDto implements ChannelIdentifiable, Serializable {
    private final String channelId;

    public UserLeaveChatEvent(String username, String channelId) {
        super(username);
        this.channelId = channelId;
    }
}