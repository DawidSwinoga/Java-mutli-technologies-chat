package com.dawid.chat.api;

/**
 * Created by Dawid on 16.11.2018 at 22:46.
 */
public interface BlockingChatService extends ChatService {
    ChannelMessageDto listenOnChannelMessage(Credential credential);
    ChannelUserChangeDto listenOnUserJoin(Credential credential);
    ChannelUserChangeDto listenOnUserLeave(Credential credential);
    ChannelInfo listenOnChannelAdd(Credential credential);
    ChannelInfo listenOnChannelRemove(Credential credential);
}
