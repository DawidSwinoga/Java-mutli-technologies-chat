package com.dawid.chat.api.impl;

import com.dawid.chat.api.Credential;
import com.dawid.chat.api.InvalidCredentialException;
import com.dawid.chat.api.UserAlreadyLoggedInException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Dawid on 17.11.2018 at 14:38.
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Credential login(String username) {
        String token = UUID.randomUUID().toString();
        String savedToken = userRepository.loginUser(username, token);

        if (!StringUtils.equals(savedToken, token)) {
            throw new UserAlreadyLoggedInException();
        }
        return new Credential(token, username);
    }

    public void validateUserCredential(Credential userCredential) {
        String userToken = userCredential.getToken();
        userRepository.getUserById(userCredential.getUsername())
                .map(User::getToken)
                .filter(userToken::equals)
                .orElseThrow(InvalidCredentialException::new);
    }

    public Optional<User> getUser(Credential userCredential) {
        return userRepository.getUserById(userCredential.getToken());
    }

    public void logout(Credential credential) {
        Optional<User> user = getUser(credential);
        user.ifPresent(userRepository::remove);
    }
}
