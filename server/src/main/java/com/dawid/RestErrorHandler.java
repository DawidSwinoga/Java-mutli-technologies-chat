package com.dawid;

import com.dawid.chat.api.channel.ChannelAlreadyExistException;
import com.dawid.chat.api.user.UserAlreadyLoggedInException;
import com.dawid.chat.commons.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by Dawid on 25.11.2018 at 00:22.
 */

@ControllerAdvice
public class RestErrorHandler {
    @ExceptionHandler(value
            = {UserAlreadyLoggedInException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ErrorCode.USER_ALREADY_EXIST, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value
            = {ChannelAlreadyExistException.class})
    protected ResponseEntity<Object> handleChannelExist(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ErrorCode.CHANNEL_ALREADY_EXIST, HttpStatus.CONFLICT);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode bodyOfResponse, HttpStatus conflict) {
        return ResponseEntity.status(conflict).body(bodyOfResponse);
    }
}
