package com.starterdemo.model;

import com.starterdemo.enums.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.ws.rs.WebApplicationException;

@Getter
public class CustomException extends Exception {
    final ExceptionCode exceptionCode;

    public CustomException(ExceptionCode exceptionCode)
    {
        super(exceptionCode.message);
        this.exceptionCode = exceptionCode;
    }

    public CustomException(ExceptionCode exceptionCode, Exception exception)
    {
        super(exceptionCode.message, exception.getCause());
        this.exceptionCode = exceptionCode;
    }
}
