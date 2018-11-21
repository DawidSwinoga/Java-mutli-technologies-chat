package com.dawid.chat.api.impl;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * Created by Dawid on 17.11.2018 at 14:43.
 */

@Repository
public class InMemoryUserRepository implements UserRepository, InMemoryRepository {
    private final Map<String, User> usernameId = new ConcurrentHashMap<>();

    @Override
    public String loginUser(String username, String token) {
        return ofNullable(usernameId.putIfAbsent(username, new User(username, token)))
                .orElseGet(() -> usernameId.get(username))
                .getToken();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return ofNullable(usernameId.get(id));
    }

    @Override
    public void remove(User user) {
        usernameId.remove(user.getUsername(), user);
    }

    @Override
    public Collection<String> getAllUsersIds() {
        return usernameId.values().stream().map(User::getToken).collect(Collectors.toSet());
    }
}
