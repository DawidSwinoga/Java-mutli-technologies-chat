package com.dawid.chat.api.impl.listener;

import com.dawid.chat.api.channel.ChannelAddedEvent;
import com.dawid.chat.api.channel.ChannelRemovedEvent;
import com.dawid.chat.api.impl.channel.ChannelService;
import com.dawid.chat.api.impl.message.MessageSender;
import com.dawid.chat.api.impl.user.UserService;
import com.dawid.chat.api.message.MessageEvent;
import com.dawid.chat.api.user.UserJoinToChatEvent;
import com.dawid.chat.api.user.UserLeaveChatEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by Dawid on 17.11.2018 at 23:14.
 */

@Component
@RequiredArgsConstructor
public class ChatEventListener {
    private final UserService userService;
    private final ChannelService channelService;
    private final MessageSender messageSender;

    @EventListener
    public void channelAdded(ChannelAddedEvent channelAddedEvent) {
        notifyAllUsers(channelAddedEvent);
    }

    @EventListener
    public void messageSend(MessageEvent messageEvent) {
        String channelId = messageEvent.getChannelId();
        notifyChannelUser(channelId, messageEvent);
    }

    @EventListener
    public void channelRemoved(ChannelRemovedEvent channelRemovedEvent) {
        notifyAllUsers(channelRemovedEvent);
    }

    @EventListener
    public void userJoin(UserJoinToChatEvent userJoinToChatEvent) {
        String channelId = userJoinToChatEvent.getChannelId();
        notifyChannelUser(channelId, userJoinToChatEvent);
    }

    @EventListener
    public void userLeave(UserLeaveChatEvent userLeaveChatEvent) {
        String channelId = userLeaveChatEvent.getChannelId();
        notifyChannelUser(channelId, userLeaveChatEvent);
    }

    private void notifyChannelUser(String channelId, Object messageEvent) {
        Collection<String> usersQueuesDestinationNames = channelService.getChannelOrThrowException(channelId)
                .getUsersQueuesDestinationNames();
        usersQueuesDestinationNames.forEach(it -> notifyUser(it, messageEvent));
    }

    private <T> void notifyUser(String queueDestinationName, T data) {
        messageSender.sendMessage(queueDestinationName, data);
    }

    private <T> void notifyAllUsers(T event) {
        Collection<String> membersIds = userService.getAllUsersQueueDestinationName();
        membersIds.forEach(it -> notifyUser(it, event));
    }
}
