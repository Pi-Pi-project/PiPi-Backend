package pipi.api.domain.post.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class AlreadyAppliedPostException extends BusinessException {
    public AlreadyAppliedPostException() {
        super(ErrorCode.ALREADY_APPLIED);
    }
}
