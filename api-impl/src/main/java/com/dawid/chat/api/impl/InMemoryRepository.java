package com.dawid.chat.api.impl;

import java.util.UUID;

/**
 * Created by Dawid on 17.11.2018 at 15:27.
 */
public interface InMemoryRepository {
    default String generateId() {
        return UUID.randomUUID().toString();
    }
}
