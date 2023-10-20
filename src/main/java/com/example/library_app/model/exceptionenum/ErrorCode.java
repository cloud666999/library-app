package com.example.library_app.model.exceptionenum;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public enum ErrorCode {
    BOOK_NOT_FOUND(404,"BOOK-001","Book not found"),
    BOOK_EXISTED(409,"BOOK-002","Book existed"),
    METHOD_ARGUMENT_NOT_VALID(400,"METHOD-003","Method argument not valid"),
    ACCOUNT_NOT_FOUND(404,"ACC-001","Account not found"),
    UNAUTHORIZED(401,"ACC-002","Unauthorized"),
    FORBIDDEN(403 ,"ACC-003","Forbiden"),
    ACCOUNT_EXISTED(409, "ACC-004", "Account existed"),
    INTERNAL_SERVER_ERROR(500, "SYS-001", "Internal server error");

    int status;

    String code;

    String message;

    ErrorCode(int status,String code, String message) {
        this.status= status;
        this.code = code;
        this.message = message;
    }
}
