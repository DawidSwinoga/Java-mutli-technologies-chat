package com.dawid.chat.api.impl.user;

import com.dawid.chat.api.user.credential.Credential;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Dawid on 17.11.2018 at 15:21.
 */

@RequiredArgsConstructor
@EqualsAndHashCode(of = {"userCredential"})
@Getter
public class User {
    private final Credential userCredential;
    private final String queueDestinationName;

    public User(String username, String token, String queueDestinationName) {
        this.queueDestinationName = queueDestinationName;
        this.userCredential = new Credential(token, username);
    }

    public String getUsername() {
        return userCredential.getUsername();
    }

    public String getToken() {
        return userCredential.getToken();
    }
}
