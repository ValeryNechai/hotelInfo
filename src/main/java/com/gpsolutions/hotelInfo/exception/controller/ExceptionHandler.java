package com.gpsolutions.hotelInfo.exception.controller;

import com.gpsolutions.hotelInfo.exception.BadRequestException;
import com.gpsolutions.hotelInfo.exception.NotFoundException;
import com.gpsolutions.hotelInfo.exception.model.ApiError;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final BadRequestException exception) {
        return new ApiError(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(final NotFoundException exception) {
        return new ApiError(exception.getMessage());
    }
}
