package com.dawid.chat.api.impl.listener;

import com.dawid.chat.api.impl.channel.ChannelAddedEvent;
import com.dawid.chat.api.impl.channel.ChannelRemovedEvent;
import com.dawid.chat.api.impl.channel.ChannelService;
import com.dawid.chat.api.impl.message.MessageEvent;
import com.dawid.chat.api.impl.message.MessageSender;
import com.dawid.chat.api.impl.user.UserJoinToChatEvent;
import com.dawid.chat.api.impl.user.UserLeaveChatEvent;
import com.dawid.chat.api.impl.user.UserService;
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
        String channelId = messageEvent.getChannelMessage().getChannelId();
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
        Collection<String> membersIds = channelService.getChannelOrThrowException(channelId).getMembersIds();
        membersIds.forEach(it -> notifyUser(it, messageEvent));
    }

    private <T> void notifyUser(String userId, T data) {
        messageSender.sendMessage(userId, data);
    }

    private <T> void notifyAllUsers(T event) {
        Collection<String> membersIds = userService.getAllUsersIds();
        membersIds.forEach(it -> notifyUser(it, event));
    }
}
