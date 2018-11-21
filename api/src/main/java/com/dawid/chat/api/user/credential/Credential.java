package com.dawid.chat.api.user.credential;

import lombok.Value;

import java.io.Serializable;

/**
 * Created by Dawid on 16.11.2018 at 22:35.
 */
@Value
public class Credential implements Serializable {
    private String token;
    private String username;
}
