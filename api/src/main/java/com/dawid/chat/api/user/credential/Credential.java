package com.dawid.chat.api.user.credential;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Dawid on 16.11.2018 at 22:35.
 */
@AllArgsConstructor
@Getter
public class Credential implements Serializable {
    private String token;
    private String username;
}
