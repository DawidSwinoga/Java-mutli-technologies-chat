package com.dawid.chat.api.impl;

import com.dawid.chat.api.ChatService;
import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.message.MessageToSend;
import com.dawid.chat.api.user.credential.Credential;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dawid on 16.11.2018 at 16:21.
 */

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final UserService userService;
    private final ChannelService channelService;

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
