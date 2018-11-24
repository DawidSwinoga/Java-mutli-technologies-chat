package com.dawid.chat.api.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dawid on 16.11.2018 at 22:38.
 */

@AllArgsConstructor
@Data
public class MessageDto implements Serializable {
    private final Date dateTime;
    private final String text;
    private final String author;
}
