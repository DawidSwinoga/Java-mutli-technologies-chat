package com.dawid;

import com.dawid.chat.api.ChatService;
import com.dawid.chat.api.channel.ChannelActionDto;
import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.channel.CreateChannelDto;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.message.MessageToSend;
import com.dawid.chat.api.user.CreateUserDto;
import com.dawid.chat.api.user.credential.Credential;
import com.dawid.chat.commons.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Dawid on 24.11.2018 at 22:10.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrl.API)
public class ChatRestController implements ChatService {
    private final ChatService chatService;

    @PostMapping(ApiUrl.LOGIN_USER)
    @Override
    public Credential loginUser(@RequestBody CreateUserDto createUserDto) {
        return chatService.loginUser(createUserDto);
    }

    @PostMapping(ApiUrl.CREATE_CHANNEL)
    @Override
    public void createChannel(@RequestBody CreateChannelDto createChannelDto) {
        chatService.createChannel(createChannelDto);
    }

    @PostMapping(ApiUrl.REMOVE_CHANNEL)
    @Override
    public void removeChannel(@RequestBody ChannelActionDto channelActionDto) {
        chatService.removeChannel(channelActionDto);
    }

    @PostMapping(ApiUrl.JOIN_TO_CHANNEL)
    @Override
    public void joinToChannel(@RequestBody ChannelActionDto channelActionDto) {
        chatService.joinToChannel(channelActionDto);
    }

    @PostMapping(ApiUrl.USER_LOGOUT)
    @Override
    public void logout(@RequestBody Credential credential) {
        chatService.logout(credential);
    }

    @PostMapping(ApiUrl.GET_ALL_CHANNELS)
    @Override
    public List<ChannelInfo> getAllChannels(@RequestBody Credential credential) {
        return chatService.getAllChannels(credential);
    }

    @Override
    @PostMapping(ApiUrl.GET_CHANNEL_MESSAGES)
    public List<MessageDto> getChannelMessage(@RequestBody ChannelActionDto channelActionDto) {
        return chatService.getChannelMessage(channelActionDto);
    }

    @PostMapping(ApiUrl.SEND_MESSAGE)
    @Override
    public void sendMessage(@RequestBody MessageToSend messageToSend) {
        chatService.sendMessage(messageToSend);
    }
}
