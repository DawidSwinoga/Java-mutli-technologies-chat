package com.dawid.chat.commons;

import com.dawid.chat.api.channel.ChannelActionDto;
import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.channel.CreateChannelDto;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.message.MessageToSend;
import com.dawid.chat.api.user.CreateUserDto;
import com.dawid.chat.api.user.UserDto;
import com.dawid.chat.api.user.credential.Credential;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.dawid.chat.commons.CreateUser.newBuilder;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

/**
 * Created by Dawid on 25.11.2018 at 14:22.
 */
public class GrpcObjectTransformer {
    public static MessageToSendDto toSendMessageDto(MessageToSend messageToSend) {
        return MessageToSendDto
                .newBuilder()
                .setChannelToken(messageToSend.getChannelToken())
                .setText(messageToSend.getText())
                .setCredential(toCredentialDto(messageToSend.getCredential()))
                .build();
    }

    public static MessageDto toMessageDto(Message message) {
        return new MessageDto(new Date(message.getDateTime()), message.getText(), message.getAuthor());
    }

    public static List<MessageDto> toMessagesDto(Iterator<Message> channelMessages) {
        Iterable<Message> messages = () -> channelMessages;
        return stream(messages.spliterator(), false).map(GrpcObjectTransformer::toMessageDto).collect(toList());
    }

    public static List<ChannelInfo> toChannelInfo(Iterator<ChannelInfoDto> channels) {
        Iterable<ChannelInfoDto> channelsInfo = () -> channels;
        return stream(channelsInfo.spliterator(), false).map(GrpcObjectTransformer::toChannelInfo).collect(toList());
    }

    public static ChannelInfo toChannelInfo(ChannelInfoDto info) {
        return new ChannelInfo(info.getChannelId(), info.getName(), toUsersDto(info.getUsersList()), toUser(info.getAdministrator()));
    }

    public static List<UserDto> toUsersDto(List<User> users) {
        return users.stream().map(GrpcObjectTransformer::toUser).collect(toList());
    }

    public static UserDto toUser(User user) {
        return new UserDto(user.getUsername());
    }

    public static ChannelAction toChannelAction(ChannelActionDto channelActionDto) {
        return ChannelAction
                .newBuilder()
                .setChannelId(channelActionDto.getChannelId())
                .setCredential(toCredentialDto(channelActionDto.getCredential()))
                .build();
    }

    public static CreateChannel toCreateChannel(CreateChannelDto createChannelDto) {
        return CreateChannel
                .newBuilder()
                .setName(createChannelDto.getName())
                .setCredential(toCredentialDto(createChannelDto.getCredential()))
                .build();
    }

    public static CredentialDto toCredentialDto(Credential credential) {
        return CredentialDto
                .newBuilder()
                .setToken(credential.getToken())
                .setUsername(credential.getUsername())
                .build();
    }

    public static CreateUserDto toCreateUserDto(CreateUser createUser) {
        return new CreateUserDto(createUser.getUsername(), createUser.getQueueDestinationName());
    }

    public static CreateUser toCreateUser(CreateUserDto createUserDto) {
        return newBuilder()
                .setUsername(createUserDto.getUsername())
                .setQueueDestinationName(createUserDto.getQueueDestinationName())
                .build();
    }

    public static CreateChannelDto toCreateChannelDto(CreateChannel createChannel) {
        return new CreateChannelDto(createChannel.getName(), toCredential(createChannel.getCredential()));
    }

    public static ChannelActionDto toChannelActionDto(ChannelAction channelAction) {
        return new ChannelActionDto(channelAction.getChannelId(), toCredential(channelAction.getCredential()));
    }

    public static Credential toCredential(CredentialDto credentialDto) {
        return new Credential(credentialDto.getToken(), credentialDto.getUsername());
    }

    public static List<ChannelInfoDto> toChannelInfoDtos(List<ChannelInfo> channels) {
        return channels.stream().map(GrpcObjectTransformer::toChannelInfo).collect(toList());
    }

    public static List<Message> toMessages(List<MessageDto> messageDtos) {
        return messageDtos.stream().map(GrpcObjectTransformer::toMessage).collect(toList());
    }

    public static MessageToSend toMessageToSend(MessageToSendDto message) {
        return new MessageToSend(message.getChannelToken(), message.getText(), toCredential(message.getCredential()));
    }

    private static Message toMessage(MessageDto messageDto) {
        return Message
                .newBuilder()
                .setDateTime(messageDto.getDateTime().getTime())
                .setText(messageDto.getText())
                .setAuthor(messageDto.getAuthor())
                .build();
    }

    private static ChannelInfoDto toChannelInfo(ChannelInfo channelInfo) {
        return ChannelInfoDto
                .newBuilder()
                .setChannelId(channelInfo.getChannelId())
                .setName(channelInfo.getName())
                .addAllUsers(toUsers(channelInfo.getUsers()))
                .setAdministrator(toUser(channelInfo.getAdministrator()))
                .build();
    }

    private static Iterable<? extends User> toUsers(List<UserDto> users) {
        return users.stream().map(GrpcObjectTransformer::toUser).collect(toList());
    }

    private static User toUser(UserDto userDto) {
        return User.newBuilder().setUsername(userDto.getUsername()).build();
    }
}
