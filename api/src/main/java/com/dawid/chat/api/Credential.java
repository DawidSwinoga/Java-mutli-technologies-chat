package com.dawid.chat.api;

import lombok.Value;

/**
 * Created by Dawid on 16.11.2018 at 22:35.
 */
@Value
public class Credential {
    private String token;
    private String username;
}
