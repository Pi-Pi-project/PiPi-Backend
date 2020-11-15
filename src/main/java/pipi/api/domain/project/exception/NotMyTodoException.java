package pipi.api.domain.project.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class NotMyTodoException extends BusinessException {
    public NotMyTodoException() {
        super(ErrorCode.NOT_MY_TODO);
    }
}
