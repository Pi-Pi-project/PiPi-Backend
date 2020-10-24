package pipi.api.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_AUTH_EMAIL(401,"Invalid Auth Email"),
    INVALID_AUTH_CODE(401,"Invalid Auth Code"),
    INVALID_TOKEN(401, "Invalid Token"),
    EXPIRED_TOKEN(403, "Expired Token"),
    USER_NOT_FOUND(404, "User Not Found"),
    POST_NOT_FOUND(404, "Post Not Found"),
    USER_ALREADY_EXISTS(409, "User is Already Exists");

    private final int status;
    private final String message;

}