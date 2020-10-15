package pipi.api.domain.user.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class UserAlreadyExistExcpetion extends BusinessException {
    public UserAlreadyExistExcpetion() {
        super(ErrorCode.USER_ALREADY_EXIST);
    }
}
