package com.dawid.chat.api.user;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Created by Dawid on 16.11.2018 at 22:28.
 */

@RequiredArgsConstructor
public class UserDto implements Serializable {
    private final String username;
}
