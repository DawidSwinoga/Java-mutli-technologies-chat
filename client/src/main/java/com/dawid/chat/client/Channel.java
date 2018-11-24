package com.dawid.chat.client;

import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.user.credential.Credential;
import lombok.Data;

import java.util.List;

import static java.util.Optional.ofNullable;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Dawid on 22.11.2018 at 12:21.
 */

@Data
public class Channel {
    private ChannelInfo channelInfo;
    private boolean containsUnreadMessages;
    private List<MessageDto> messages;

    public Channel(ChannelInfo channelInfo) {
        this.channelInfo = channelInfo;
        messages = observableArrayList();
    }

    public String getName() {
        return channelInfo.getName();
    }

    public String getChannelId() {
        return channelInfo.getChannelId();
    }

    public boolean isTheSameId(String channelId) {
        return getChannelId().equals(channelId);
    }

    public void addMessage(MessageDto messageDto) {
        messages.add(messageDto);
    }

    public boolean isAdministrator(Credential credential) {
        return channelInfo.getAdministrator().getUsername().equals(credential.getUsername());
    }

    public boolean isSubscribed(Credential credential) {
        return ofNullable(channelInfo.getUsers())
                .map(users -> users
                        .stream()
                        .anyMatch(it -> it.getUsername().equals(credential.getUsername())))
                .orElse(false);
    }
}
