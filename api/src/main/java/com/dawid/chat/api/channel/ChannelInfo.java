package com.dawid.chat.api.channel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Created by Dawid on 16.11.2018 at 22:26.
 */

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class ChannelInfo implements Serializable {
    private final String channelId;
    private final String name;
}
