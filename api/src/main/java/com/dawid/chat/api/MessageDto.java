package com.dawid.chat.api;

import lombok.AllArgsConstructor;

import java.time.Instant;

/**
 * Created by Dawid on 16.11.2018 at 22:38.
 */

@AllArgsConstructor
public class MessageDto {
    private final Instant dateTime;
    private final String text;
    private final String author;
}
