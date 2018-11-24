package com.dawid.chat.api.channel;

import com.dawid.chat.api.user.UserDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dawid on 16.11.2018 at 22:26.
 */

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class ChannelInfo implements Serializable {
    private final String channelId;
    private final String name;
    private final List<UserDto> users;
    private final UserDto administrator;
}
