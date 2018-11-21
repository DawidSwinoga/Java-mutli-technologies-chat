package com.dawid.chat.api.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;

/**
 * Created by Dawid on 16.11.2018 at 22:38.
 */

@AllArgsConstructor
@Getter
public class MessageDto implements Serializable {
    private final Instant dateTime;
    private final String text;
    private final String author;
}
