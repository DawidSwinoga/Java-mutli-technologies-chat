package com.dawid.chat.api.impl.channel;

import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.impl.message.Message;
import com.dawid.chat.api.impl.user.User;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.user.UserDto;
import com.dawid.chat.api.user.UserIsNotChannelMemberException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by Dawid on 17.11.2018 at 15:17.
 */

public class Channel {
    private final String id;
    private final String name;
    private final User administrator;
    private final Set<User> users;
    private final List<Message> messages;

    public Channel(String id, String name, User administrator) {
        this.id = id;
        this.name = name;
        this.administrator = administrator;
        this.users = new CopyOnWriteArraySet<>(Collections.singleton(administrator));
        this.messages = new CopyOnWriteArrayList<>();
    }


    public boolean isIdValid(String id) {
        return this.id.equals(id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public ChannelInfo toChannelInfo() {
        return new ChannelInfo(id, name, getUsersDtos(), new UserDto(administrator.getUsername()));
    }

    public List<UserDto> getUsersDtos() {
        return users.stream().map(it -> new UserDto(it.getUsername())).collect(toList());
    }

    public void validChannelMember(User user) {
        boolean contains = users.contains(user);
        if (!contains) {
            throw new UserIsNotChannelMemberException();
        }
    }

    public List<MessageDto> getMessages() {
        return messages.stream().map(this::toMessageDto).collect(toList());
    }

    private MessageDto toMessageDto(Message message) {
        return new MessageDto(message.getDateTime(), message.getText(), message.getAuthor().getUsername());
    }

    public Message addMessage(User user, String text) {
        Message message = new Message(user, text);
        messages.add(message);
        return message;
    }

    public Collection<String> getMembersIds() {
        return users.stream().map(User::getToken).collect(toSet());
    }

    public Collection<String> getUsersQueuesDestinationNames() {
        return users.stream().map(User::getQueueDestinationName).collect(Collectors.toList());
    }

    public boolean isAdministrator(User user) {
        return administrator.equals(user);
    }
}
