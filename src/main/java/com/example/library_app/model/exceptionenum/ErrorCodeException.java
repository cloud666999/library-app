package com.example.library_app.model.exceptionenum;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ErrorCodeException extends RuntimeException {
    ErrorCode errorCode;

    String description;

    public ErrorCodeException(ErrorCode errorCode,String description) {
        super(errorCode.getCode()+ ":" + errorCode.getMessage());
        this.errorCode= errorCode;
        this.description = description;
    }
}
