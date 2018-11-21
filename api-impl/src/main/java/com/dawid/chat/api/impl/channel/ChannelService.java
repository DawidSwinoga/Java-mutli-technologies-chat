package com.dawid.chat.api.impl.channel;

import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.channel.ChannelNotExistException;
import com.dawid.chat.api.impl.user.User;
import com.dawid.chat.api.impl.user.UserService;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.user.credential.Credential;
import com.dawid.chat.api.user.credential.InvalidCredentialException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by Dawid on 17.11.2018 at 14:38.
 */

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final UserService userService;
    private final ChannelRepository channelRepository;

    public ChannelInfo createChannel(String channelName, Credential userCredential) {
        User user = getUserOrThrowException(userCredential);
        Channel channel = channelRepository.createChannel(channelName, user);
        return new ChannelInfo(channel.getId(), channel.getName());
    }

    private User getUserOrThrowException(Credential userCredential) {
        return userService.getUser(userCredential).orElseThrow(InvalidCredentialException::new);
    }

    public void joinToChannel(String id, Credential credential) {
        Channel channel = getChannelOrThrowException(id);
        User user = getUserOrThrowException(credential);
        channel.addUser(user);
    }

    public Channel getChannelOrThrowException(String id) {
        return channelRepository.getById(id).orElseThrow(ChannelNotExistException::new);
    }

    public void leaveChannel(String channelToken, Credential credential) {
        Channel channel = getChannelOrThrowException(channelToken);
        User user = getUserOrThrowException(credential);
        channel.removeUser(user);
    }

    public List<ChannelInfo> getAllChannels(Credential credential) {
        userService.validateUserCredential(credential);
        return channelRepository.getAll().stream().map(Channel::toChannelInfo).collect(toList());
    }

    public void leaveAllChannels(Credential credential) {
        User user = getUserOrThrowException(credential);
        channelRepository.removeFromChannels(user);
    }

    public List<MessageDto> getChannelMessage(String channelToken, Credential credential) {
        User user = getUserOrThrowException(credential);
        Channel channel = getChannelOrThrowException(channelToken);
        channel.validChannelMember(user);
        return channel.getMessages();
    }

    public void addMessage(String channelToken, String text, Credential credential) {
        User user = getUserOrThrowException(credential);
        Channel channel = getChannelOrThrowException(channelToken);
        channel.validChannelMember(user);
        channel.addMessage(user, text);
    }
}
