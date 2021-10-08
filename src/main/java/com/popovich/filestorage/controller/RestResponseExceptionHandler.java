package com.popovich.filestorage.controller;

import com.popovich.filestorage.dto.ErrorDto;
import lombok.Builder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RestResponseExceptionHandler {
    private final ZoneId zoneId = ZoneId.of("Europe/Moscow");

    @Builder
    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<Object> handleException(Exception ex) {
        var status = HttpStatus.BAD_REQUEST;
        var customError = ErrorDto.builder()
                .success(false)
                .error(ExceptionUtils.getMessage(ex))
                .root(ExceptionUtils.getStackTrace(ex))
                .date(ZonedDateTime.ofInstant(Instant.now(), zoneId))
                .build();
        return new ResponseEntity<>(customError, status);
    }
}