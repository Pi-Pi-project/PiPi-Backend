package pipi.api.domain.post.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class MyProjectException extends BusinessException {
    public MyProjectException() {
        super(ErrorCode.MY_POST);
    }
}

