package pipi.api.domain.project.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class TodoNotFoundException extends BusinessException {
    public TodoNotFoundException() {
        super(ErrorCode.TODO_NOT_FOUND);
    }
}
