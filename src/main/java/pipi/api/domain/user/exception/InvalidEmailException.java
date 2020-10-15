package pipi.api.domain.user.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class InvalidEmailException extends BusinessException {
    public InvalidEmailException() {
        super(ErrorCode.INVALID_EMAIL);
    }
}
