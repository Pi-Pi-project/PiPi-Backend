package pipi.api.domain.project.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class NotProjectManagerException extends BusinessException {
    public NotProjectManagerException() {
        super(ErrorCode.NOT_PM);
    }
}
