package com.dawid.chat.api.channel;

import com.dawid.chat.api.user.credential.Credential;
import lombok.Value;

import java.io.Serializable;

/**
 * Created by Dawid on 24.11.2018 at 21:54.
 */
@Value
public class CreateChannelDto implements Serializable {
    private final String name;
    private final Credential credential;
}
