package com.dawid.chat.client.rest;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.dawid.chat.commons.ApiUrl.API;
import static com.dawid.chat.commons.ApiUrl.CREATE_CHANNEL;
import static com.dawid.chat.commons.ApiUrl.GET_ALL_CHANNELS;
import static com.dawid.chat.commons.ApiUrl.GET_CHANNEL_MESSAGES;
import static com.dawid.chat.commons.ApiUrl.JOIN_TO_CHANNEL;
import static com.dawid.chat.commons.ApiUrl.LOGIN_USER;
import static com.dawid.chat.commons.ApiUrl.REMOVE_CHANNEL;
import static com.dawid.chat.commons.ApiUrl.SEND_MESSAGE;
import static com.dawid.chat.commons.ApiUrl.USER_LOGOUT;

/**
 * Created by Dawid on 24.11.2018 at 22:31.
 */

@Slf4j
@RequiredArgsConstructor
public class RestChatApi implements ChatService {
    private final RestTemplate restTemplate;
    private final String HOST_NAME;

    @Override
    public Credential loginUser(CreateUserDto createUserDto) {
        return post(LOGIN_USER, createUserDto, Credential.class);
    }

    @Override
    public void createChannel(CreateChannelDto createChannelDto) {
        post(CREATE_CHANNEL, createChannelDto);
    }

    @Override
    public void removeChannel(ChannelActionDto channelActionDto) {
        post(REMOVE_CHANNEL, channelActionDto);
    }

    @Override
    public void joinToChannel(ChannelActionDto channelActionDto) {
        post(JOIN_TO_CHANNEL, channelActionDto);
    }

    @Override
    public void logout(Credential credential) {
        post(USER_LOGOUT, credential);
    }

    @Override
    public List<ChannelInfo> getAllChannels(Credential credential) {
        try {
            RequestEntity<Credential> requestEntity = RequestEntity
                    .post(new URI(HOST_NAME + API + GET_ALL_CHANNELS))
                    .body(credential);
            return restTemplate.exchange(
                    API + GET_ALL_CHANNELS,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<ChannelInfo>>() {
                    }).getBody();
        } catch (URISyntaxException e) {
            log.error("Rest template error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MessageDto> getChannelMessage(ChannelActionDto channelActionDto) {
        try {
            RequestEntity<ChannelActionDto> requestEntity = RequestEntity
                    .post(new URI(HOST_NAME + API + GET_CHANNEL_MESSAGES))
                    .body(channelActionDto);
            return restTemplate.exchange(
                    API + GET_ALL_CHANNELS,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<MessageDto>>() {
                    }).getBody();
        } catch (URISyntaxException e) {
            log.error("Rest template error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(MessageToSend messageToSend) {
        post(SEND_MESSAGE, messageToSend);
    }

    private <T> T post(String url, Object requestObject, Class<T> responseType) {
        return restTemplate.postForObject(HOST_NAME + ApiUrl.API + url, requestObject, responseType);
    }

    private void post(String uri, Object requestObject) {
        post(uri, requestObject, Void.class);
    }
}
