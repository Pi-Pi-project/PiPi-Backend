package pipi.api.domain.auth.exception;

import pipi.api.domain.user.exception.InvalidAuthEmailException;
import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
