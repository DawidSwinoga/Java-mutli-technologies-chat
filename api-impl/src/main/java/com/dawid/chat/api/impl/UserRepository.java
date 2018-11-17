package com.dawid.chat.api.impl;

import java.util.Optional;

/**
 * Created by Dawid on 17.11.2018 at 14:43.
 */
public interface UserRepository {
    String loginUser(String username, String token);

    Optional<User> getUserById(String id);

    void remove(User user);
}
