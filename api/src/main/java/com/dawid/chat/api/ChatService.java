package com.dawid.chat.api;

import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.message.MessageToSend;
import com.dawid.chat.api.user.credential.Credential;

import java.util.List;

/**
 * Created by Dawid on 16.11.2018 at 16:21.
 */
public interface ChatService {
    Credential loginUser(String username, String queueDestinationName);
    ChannelInfo createChannel(String name, Credential credential);
    void removeChannel(String channelId, Credential credential);
    void joinToChannel(String channelToken, Credential credential);
    void leaveChannel(String channelToken, Credential credential);
    void logout(Credential credential);
    List<ChannelInfo> getAllChannels(Credential credential);
    List<MessageDto> getChannelMessage(String channelToken, Credential credential);
    void sendMessage(MessageToSend messageToSend);
}
