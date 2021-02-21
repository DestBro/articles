package org.example.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ApiErrorResponse {

    private final LocalDateTime timeStamp;

    private final Integer status;

    private final String error;

    private final String message;

    private final String detail;
}