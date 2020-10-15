package pipi.api.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_REQUEST(400, "Bad Request"),
    USER_NOT_FOUND(404, "User Not Found");

    private final int status;
    private final String message;
}
