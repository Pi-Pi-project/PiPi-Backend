package pipi.api.domain.project.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class TooManyMemberException extends BusinessException {
    public TooManyMemberException() {
        super(ErrorCode.TOO_MANY_MEMBER);
    }
}
