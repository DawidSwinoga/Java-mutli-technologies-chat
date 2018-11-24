package com.dawid.chat.api.user;

import lombok.Value;

import java.io.Serializable;

/**
 * Created by Dawid on 24.11.2018 at 21:48.
 */

@Value
public class CreateUserDto implements Serializable {
    private final String username;
    private final String queueDestinationName;
}
