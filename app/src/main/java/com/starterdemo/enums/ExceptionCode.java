package com.starterdemo.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionCode {
    INTERNAL_SERVER_ERROR(1, 500, "There is an error in processing the request"),
    INVALID_EMAIL_ID(2, 520, "Please provide a valid email id"),
    INVALID_FIRST_NAME (3, 520, "Please provide a valid first name"),
    INVALID_LAST_NAME (4, 520, "Please provide a valid last name"),
    RESOURCE_NOT_FOUND (5, 404, "Resource not found");

    public final int errorCode;
    public final int responseCode;
    public final String message;
}
