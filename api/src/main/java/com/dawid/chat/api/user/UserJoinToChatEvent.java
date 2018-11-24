package com.dawid.chat.api.user;

import com.dawid.chat.api.channel.ChannelIdentifiable;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * Created by Dawid on 17.11.2018 at 22:56.
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class UserJoinToChatEvent extends UserDto implements ChannelIdentifiable, Serializable {
    private final String channelId;

    public UserJoinToChatEvent(String username, String channelId) {
        super(username);
        this.channelId = channelId;
    }
}