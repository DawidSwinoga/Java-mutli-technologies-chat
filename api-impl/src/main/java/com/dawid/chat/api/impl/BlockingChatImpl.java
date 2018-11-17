package com.dawid.chat.api.impl;

import com.dawid.chat.api.BlockingChatService;
import com.dawid.chat.api.ChannelInfo;
import com.dawid.chat.api.ChannelMessageDto;
import com.dawid.chat.api.ChannelUserChangeDto;
import com.dawid.chat.api.Credential;
import com.dawid.chat.api.MessageDto;
import com.dawid.chat.api.MessageToSend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dawid on 16.11.2018 at 16:21.
 */

@Service
@RequiredArgsConstructor
public class BlockingChatImpl implements BlockingChatService {
    private final UserService userService;
    private final ChannelService channelService;
    private final BlockingMessageNotificator messageNotificator;

    @Override
    public ChannelMessageDto listenOnChannelMessage(Credential credential) {
        return messageNotificator.listenOnChannelMessage(credential);
    }

    @Override
    public ChannelUserChangeDto listenOnUserJoin(Credential credential) {
        return messageNotificator.listenOnUserJoin(credential);
    }

    @Override
    public ChannelUserChangeDto listenOnUserLeave(Credential credential) {
        return messageNotificator.listenOnUserLeave(credential);
    }

    @Override
    public ChannelInfo listenOnChannelAdd(Credential credential) {
        return messageNotificator.listenOnChannelAdd(credential);
    }

    @Override
    public ChannelInfo listenOnChannelRemove(Credential credential) {
        return messageNotificator.listenOnChannelRemove(credential);
    }

    @Override
    public Credential loginUser(String username) {
        return userService.login(username);
    }

    @Override
    public ChannelInfo createChannel(String name, Credential credential) {
        return channelService.createChannel(name, credential);
    }

    @Override
    public void joinToChannel(String channelToken, Credential credential) {
        channelService.joinToChannel(channelToken, credential);
    }

    @Override
    public void leaveChannel(String channelToken, Credential credential) {
        channelService.leaveChannel(channelToken, credential);
    }

    @Override
    public void logout(Credential credential) {
        channelService.leaveAllChannels(credential);
        userService.logout(credential);
    }

    @Override
    public List<ChannelInfo> getAllChannels(Credential credential) {
        return channelService.getAllChannels(credential);
    }

    @Override
    public List<MessageDto> getChannelMessage(String channelToken, Credential credential) {
        return channelService.getChannelMessage(channelToken, credential);
    }

    @Override
    public void sendMessage(MessageToSend msg) {
        channelService.addMessage(msg.getChannelToken(), msg.getText(), msg.getCredential());
    }
}
