package com.dawid.chat.api;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Created by Dawid on 17.11.2018 at 20:52.
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class ChannelUserChangeDto extends UserDto {
    private final String channelId;
}
