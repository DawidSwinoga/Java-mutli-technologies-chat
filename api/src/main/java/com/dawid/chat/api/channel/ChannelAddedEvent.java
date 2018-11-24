package com.dawid.chat.api.channel;

import com.dawid.chat.api.user.UserDto;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by Dawid on 18.11.2018 at 00:12.
 */

@EqualsAndHashCode(callSuper = true)
public class ChannelAddedEvent extends ChannelInfo {
    public ChannelAddedEvent(String channelId, String name, List<UserDto> users, UserDto administrator) {
        super(channelId, name, users, administrator);
    }

    public ChannelAddedEvent(ChannelInfo channelInfo) {
        super(channelInfo.getChannelId(), channelInfo.getName(), channelInfo.getUsers(),
                new UserDto(channelInfo.getAdministrator().getUsername()));
    }
}
