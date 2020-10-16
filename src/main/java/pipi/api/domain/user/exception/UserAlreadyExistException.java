package pipi.api.domain.user.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class UserAlreadyExistException extends BusinessException {
    public UserAlreadyExistException() {
        super(ErrorCode.USER_ALREADY_EXISTS);
    }
}
