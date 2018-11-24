package com.dawid.chat.api;

import com.dawid.chat.api.channel.ChannelActionDto;
import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.channel.CreateChannelDto;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.message.MessageToSend;
import com.dawid.chat.api.user.CreateUserDto;
import com.dawid.chat.api.user.credential.Credential;

import java.util.List;

/**
 * Created by Dawid on 16.11.2018 at 16:21.
 */
public interface ChatService {
    Credential loginUser(CreateUserDto createUserDto);
    void createChannel(CreateChannelDto createChannelDto);
    void removeChannel(ChannelActionDto channelActionDto);
    void joinToChannel(ChannelActionDto channelActionDto);
    void logout(Credential credential);
    List<ChannelInfo> getAllChannels(Credential credential);
    List<MessageDto> getChannelMessage(ChannelActionDto channelActionDto);
    void sendMessage(MessageToSend messageToSend);
}
