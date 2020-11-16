package pipi.api.domain.project.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class ProjectNotFoundException extends BusinessException {
    public ProjectNotFoundException() {
        super(ErrorCode.PROJECT_NOT_FOUND);
    }
}
