package com.example.library_app.config.handle;

import com.example.library_app.model.exception.ErrorResponse;
import com.example.library_app.model.exceptionenum.ErrorCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // sử dụng errorResponse sau khi có thêm enum ErrorCode
    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorCodeException exception) {
        var errCode= exception.getErrorCode();
        var errResponse = new ErrorResponse(errCode.getStatus(), errCode.getCode(), errCode.getMessage(), exception.getDescription());

        return ResponseEntity.status(errCode.getStatus()).body(errResponse);
    }

    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity<ErrorResponse> handleBookException(ErrorCodeException exception) {
            return buildResponseEntity(exception);
    }

}
