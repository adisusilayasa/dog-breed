package com.dogbreed.models.responses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Custom exception class to encapsulate the error message
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
