package com.dawid;

import com.dawid.chat.api.ChatService;
import com.dawid.chat.api.channel.ChannelAlreadyExistException;
import com.dawid.chat.api.channel.ChannelInfo;
import com.dawid.chat.api.message.MessageDto;
import com.dawid.chat.api.user.UserAlreadyLoggedInException;
import com.dawid.chat.api.user.credential.Credential;
import com.dawid.chat.commons.ChannelAction;
import com.dawid.chat.commons.ChannelInfoDto;
import com.dawid.chat.commons.ChatServiceGrpc;
import com.dawid.chat.commons.CreateChannel;
import com.dawid.chat.commons.CreateUser;
import com.dawid.chat.commons.CredentialDto;
import com.dawid.chat.commons.Empty;
import com.dawid.chat.commons.Message;
import com.dawid.chat.commons.MessageToSendDto;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

import java.util.List;

import static com.dawid.chat.commons.GrpcObjectTransformer.toChannelActionDto;
import static com.dawid.chat.commons.GrpcObjectTransformer.toChannelInfoDtos;
import static com.dawid.chat.commons.GrpcObjectTransformer.toCreateChannelDto;
import static com.dawid.chat.commons.GrpcObjectTransformer.toCreateUserDto;
import static com.dawid.chat.commons.GrpcObjectTransformer.toCredential;
import static com.dawid.chat.commons.GrpcObjectTransformer.toCredentialDto;
import static com.dawid.chat.commons.GrpcObjectTransformer.toMessageToSend;
import static com.dawid.chat.commons.GrpcObjectTransformer.toMessages;

/**
 * Created by Dawid on 25.11.2018 at 14:33.
 */

@GRpcService
@RequiredArgsConstructor
public class GrpcService extends ChatServiceGrpc.ChatServiceImplBase {
    private final ChatService service;

    @Override
    public void loginUser(CreateUser request, StreamObserver<CredentialDto> responseObserver) {
        try {
            Credential credential = service.loginUser(toCreateUserDto(request));
            responseObserver.onNext(toCredentialDto(credential));
            responseObserver.onCompleted();
        } catch (UserAlreadyLoggedInException e) {
            responseObserver.onError(new StatusRuntimeException(Status.ALREADY_EXISTS));
        }
    }

    @Override
    public void createChannel(CreateChannel request, StreamObserver<Empty> responseObserver) {
        try {
            service.createChannel(toCreateChannelDto(request));
            emptyResponse(responseObserver);
            responseObserver.onCompleted();
        } catch (ChannelAlreadyExistException e) {
            responseObserver.onError(new StatusRuntimeException(Status.ALREADY_EXISTS));
        }
    }

    @Override
    public void removeChannel(ChannelAction request, StreamObserver<Empty> responseObserver) {
        service.removeChannel(toChannelActionDto(request));
        emptyResponse(responseObserver);
        responseObserver.onCompleted();
    }

    private void emptyResponse(StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
    }

    @Override
    public void joinToChannel(ChannelAction request, StreamObserver<Empty> responseObserver) {
        service.joinToChannel(toChannelActionDto(request));
        emptyResponse(responseObserver);
        responseObserver.onCompleted();
    }

    @Override
    public void logout(CredentialDto request, StreamObserver<Empty> responseObserver) {
        service.logout(toCredential(request));
        emptyResponse(responseObserver);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllChannels(CredentialDto request, StreamObserver<ChannelInfoDto> responseObserver) {
        List<ChannelInfo> channels = service.getAllChannels(toCredential(request));
        List<ChannelInfoDto> channelInfoDtos = toChannelInfoDtos(channels);
        channelInfoDtos.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getChannelMessages(ChannelAction request, StreamObserver<Message> responseObserver) {
        List<MessageDto> channelMessage = service.getChannelMessage(toChannelActionDto(request));
        List<Message> messages = toMessages(channelMessage);
        messages.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void sendMessage(MessageToSendDto request, StreamObserver<Empty> responseObserver) {
        service.sendMessage(toMessageToSend(request));
        emptyResponse(responseObserver);
        responseObserver.onCompleted();
    }
}
