package com.dawid.chat.api.impl;

import com.dawid.chat.api.ChatService;
import com.dawid.chat.api.channel.ChannelActionDto;
import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.channel.CreateChannelDto;
import com.dawid.chat.api.impl.channel.ChannelService;
import com.dawid.chat.api.impl.user.UserService;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.message.MessageToSend;
import com.dawid.chat.api.user.CreateUserDto;
import com.dawid.chat.api.user.credential.Credential;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dawid on 16.11.2018 at 16:21.
 */

@Primary
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final UserService userService;
    private final ChannelService channelService;

    @Override
    public Credential loginUser(CreateUserDto createUserDto) {
        return userService.login(createUserDto.getUsername(), createUserDto.getQueueDestinationName());
    }

    @Override
    public void createChannel(CreateChannelDto createChannelDto) {
        channelService.createChannel(createChannelDto.getName(), createChannelDto.getCredential());
    }

    @Override
    public void removeChannel(ChannelActionDto channelActionDto) {
        channelService.removeChannel(channelActionDto.getChannelId(), channelActionDto.getCredential());
    }

    @Override
    public void joinToChannel(ChannelActionDto channelActionDto) {
        channelService.joinToChannel(channelActionDto.getChannelId(), channelActionDto.getCredential());
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
    public List<MessageDto> getChannelMessage(ChannelActionDto channelActionDto) {
        return channelService.getChannelMessage(channelActionDto.getChannelId(), channelActionDto.getCredential());
    }

    @Override
    public void sendMessage(MessageToSend msg) {
        channelService.addMessage(msg.getChannelToken(), msg.getText(), msg.getCredential());
    }
}
