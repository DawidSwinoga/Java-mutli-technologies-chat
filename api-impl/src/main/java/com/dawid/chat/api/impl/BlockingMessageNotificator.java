package com.dawid.chat.api.impl;

import com.dawid.chat.api.ChannelInfo;
import com.dawid.chat.api.ChannelMessageDto;
import com.dawid.chat.api.ChannelUserChangeDto;
import com.dawid.chat.api.Credential;
import com.dawid.chat.api.ListenerTimeOutException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dawid on 17.11.2018 at 20:40.
 */

@Service
@RequiredArgsConstructor
public class BlockingMessageNotificator {
    public static final int INITIAL_QUEUE_CAPACITY = 10;
    public static final int TIMEOUT_MINUTES = 5;
    private final Map<String, BlockingQueue<ChannelMessageDto>> userIdChannelMessageQueue = new ConcurrentHashMap<>();
    private final Map<String, BlockingQueue<ChannelUserChangeDto>> userIdJoinChannelMessageQueue = new ConcurrentHashMap<>();
    private final Map<String, BlockingQueue<ChannelUserChangeDto>> userIdLeaveChannelMessageQueue = new ConcurrentHashMap<>();
    private final Map<String, BlockingQueue<ChannelInfo>> userIdAddChannelMessageQueue = new ConcurrentHashMap<>();
    private final Map<String, BlockingQueue<ChannelInfo>> userIdRemoveChannelMessageQueue = new ConcurrentHashMap<>();
    private final UserService userService;


    public ChannelMessageDto listenOnChannelMessage(Credential credential) {
        return get(credential, userIdChannelMessageQueue);
    }

    public ChannelUserChangeDto listenOnUserJoin(Credential credential) {
        return get(credential, userIdJoinChannelMessageQueue);
    }

    public ChannelUserChangeDto listenOnUserLeave(Credential credential) {
        return get(credential, userIdLeaveChannelMessageQueue);
    }

    public ChannelInfo listenOnChannelAdd(Credential credential) {
        return get(credential, userIdAddChannelMessageQueue);
    }

    public ChannelInfo listenOnChannelRemove(Credential credential) {
        return get(credential, userIdRemoveChannelMessageQueue);
    }

    private <T> T get(Credential credential, Map<String, BlockingQueue<T>> queue) {
        userService.validateUserCredential(credential);
        BlockingQueue<T> channelMessage = queue
                .putIfAbsent(credential.getToken(), new ArrayBlockingQueue<>(INITIAL_QUEUE_CAPACITY));
        try {
            return channelMessage.poll(TIMEOUT_MINUTES, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new ListenerTimeOutException();
        }
    }
}
