package com.dawid.chat.client.grpc;

import com.dawid.chat.api.ChatService;
import com.dawid.chat.api.channel.ChannelActionDto;
import com.dawid.chat.api.channel.ChannelAlreadyExistException;
import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.channel.CreateChannelDto;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.message.MessageToSend;
import com.dawid.chat.api.user.CreateUserDto;
import com.dawid.chat.api.user.UserAlreadyLoggedInException;
import com.dawid.chat.api.user.credential.Credential;
import com.dawid.chat.commons.ChatServiceGrpc;
import com.dawid.chat.commons.CredentialDto;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.dawid.chat.commons.GrpcObjectTransformer.toChannelAction;
import static com.dawid.chat.commons.GrpcObjectTransformer.toChannelInfo;
import static com.dawid.chat.commons.GrpcObjectTransformer.toCreateChannel;
import static com.dawid.chat.commons.GrpcObjectTransformer.toCreateUser;
import static com.dawid.chat.commons.GrpcObjectTransformer.toCredential;
import static com.dawid.chat.commons.GrpcObjectTransformer.toCredentialDto;
import static com.dawid.chat.commons.GrpcObjectTransformer.toMessagesDto;
import static com.dawid.chat.commons.GrpcObjectTransformer.toSendMessageDto;

/**
 * Created by Dawid on 25.11.2018 at 13:27.
 */

@RequiredArgsConstructor
@Slf4j
public class GrpcChatService implements ChatService {
    private final ChatServiceGrpc.ChatServiceBlockingStub service;

    @Override
    public Credential loginUser(CreateUserDto createUserDto) {
        try {
            CredentialDto credentialDto = service.loginUser(toCreateUser(createUserDto));
            return toCredential(credentialDto);
        } catch (StatusRuntimeException e) {
            log.error("Exception", e);
            resolveLoginException(e);
            throw new RuntimeException(e);
        }
    }

    private void resolveLoginException(StatusRuntimeException e) {
        if (e.getStatus().equals(Status.ALREADY_EXISTS)) {
            throw new UserAlreadyLoggedInException();
        }
    }


    @Override
    public void createChannel(CreateChannelDto createChannelDto) {
        try {
            service.createChannel(toCreateChannel(createChannelDto));
        } catch (StatusRuntimeException e) {
            log.error("Exception", e);
            resolveCreateChannelException(e);
            throw new RuntimeException(e);
        }
    }

    private void resolveCreateChannelException(StatusRuntimeException e) {
        if (e.getStatus().equals(Status.ALREADY_EXISTS)) {
            throw new ChannelAlreadyExistException();
        }
    }


    @Override
    public void removeChannel(ChannelActionDto channelActionDto) {
        service.removeChannel(toChannelAction(channelActionDto));
    }


    @Override
    public void joinToChannel(ChannelActionDto channelActionDto) {
        service.joinToChannel(toChannelAction(channelActionDto));
    }

    @Override
    public void logout(Credential credential) {
        service.logout(toCredentialDto(credential));
    }

    @Override
    public List<ChannelInfo> getAllChannels(Credential credential) {
        return toChannelInfo(service.getAllChannels(toCredentialDto(credential)));
    }


    @Override
    public List<MessageDto> getChannelMessage(ChannelActionDto channelActionDto) {
        return toMessagesDto(service.getChannelMessages(toChannelAction(channelActionDto)));
    }


    @Override
    public void sendMessage(MessageToSend messageToSend) {
        service.sendMessage(toSendMessageDto(messageToSend));
    }
}
