package com.dawid.chat.api.channel;

import com.dawid.chat.api.user.credential.Credential;
import lombok.Value;

import java.io.Serializable;

/**
 * Created by Dawid on 24.11.2018 at 22:01.
 */
@Value
public class ChannelActionDto implements Serializable {
    private final String channelId;
    private final Credential credential;
}
