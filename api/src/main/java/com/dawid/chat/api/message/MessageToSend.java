package com.dawid.chat.api.message;

import com.dawid.chat.api.user.credential.Credential;
import lombok.Value;

import java.io.Serializable;

/**
 * Created by Dawid on 16.11.2018 at 22:43.
 */

@Value
public class MessageToSend implements Serializable {
    private String channelToken;
    private String text;
    private Credential credential;
}
