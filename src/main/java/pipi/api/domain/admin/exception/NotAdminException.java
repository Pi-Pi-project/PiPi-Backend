package pipi.api.domain.admin.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class NotAdminException extends BusinessException {
    public NotAdminException() {
        super(ErrorCode.NOT_ADMIN);
    }
}
