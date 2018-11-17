package com.dawid.chat.api;

import lombok.Data;

/**
 * Created by Dawid on 16.11.2018 at 22:43.
 */

@Data
public class MessageToSend {
    private String channelToken;
    private String text;
    private Credential credential;
}
