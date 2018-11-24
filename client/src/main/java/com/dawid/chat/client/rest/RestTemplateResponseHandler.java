package com.dawid.chat.client.rest;

import com.dawid.chat.api.channel.ChannelAlreadyExistException;
import com.dawid.chat.api.user.UserAlreadyLoggedInException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.dawid.chat.commons.ErrorCode.valueOf;

/**
 * Created by Dawid on 25.11.2018 at 00:17.
 */

@Component
@Slf4j
public class RestTemplateResponseHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode() != HttpStatus.OK;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        switch (valueOf(IOUtils.toString(response.getBody(), Charset.defaultCharset()))) {
            case USER_ALREADY_EXIST:
                throw new UserAlreadyLoggedInException();
            case CHANNEL_ALREADY_EXIST:
                throw new ChannelAlreadyExistException();
            default:
                    throw new RuntimeException();
        }
    }
}
