package com.dawid.chat.api.impl.user;

import com.dawid.chat.api.user.UserAlreadyLoggedInException;
import com.dawid.chat.api.user.credential.Credential;
import com.dawid.chat.api.user.credential.InvalidCredentialException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Dawid on 17.11.2018 at 14:38.
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Credential login(String username, String queueDestinationName) {
        String token = UUID.randomUUID().toString();
        String savedToken = userRepository.loginUser(username, token, queueDestinationName);

        if (!StringUtils.equals(savedToken, token)) {
            throw new UserAlreadyLoggedInException();
        }
        return new Credential(token, username);
    }

    public void validateUserCredential(Credential userCredential) {
        String userToken = userCredential.getToken();
        userRepository.getUserById(userToken)
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

    public Collection<String> getAllUsersIds() {
        return userRepository.getAllUsersIds();
    }

    public Collection<String> getAllUsersQueueDestinationName() {
        return userRepository.getAllUsersQueueDestinationNames();
    }
}
