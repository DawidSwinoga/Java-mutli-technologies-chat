package com.dawid.chat.api.impl;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dawid on 17.11.2018 at 14:43.
 */

@Repository
public class InMemoryUserRepository implements UserRepository, InMemoryRepository {
    private final Map<String, User> usernameId = new ConcurrentHashMap<>();

    @Override
    public String loginUser(String username, String token) {
        return usernameId.putIfAbsent(username, new User(username, token)).getToken();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(usernameId.get(id));
    }

    @Override
    public void remove(User user) {
        usernameId.remove(user.getUsername(), user);
    }
}
