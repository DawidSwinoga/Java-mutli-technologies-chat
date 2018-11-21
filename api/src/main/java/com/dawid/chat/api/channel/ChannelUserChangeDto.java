package com.dawid.chat.api.channel;

import com.dawid.chat.api.user.UserDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Dawid on 17.11.2018 at 20:52.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class ChannelUserChangeDto extends UserDto implements ChannelIdentifiable, Serializable {
    private final String channelId;

    public ChannelUserChangeDto(String username, String channelId) {
        super(username);
        this.channelId = channelId;
    }
}
