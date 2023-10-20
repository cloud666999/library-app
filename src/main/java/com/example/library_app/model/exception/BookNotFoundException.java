package com.example.library_app.model.exception;

import com.example.library_app.model.exceptionenum.ErrorCode;
import com.example.library_app.model.exceptionenum.ErrorCodeException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BookNotFoundException  extends ErrorCodeException {
    public BookNotFoundException(ErrorCode errorCode, String description) {
        super(errorCode, description);
    }
}
