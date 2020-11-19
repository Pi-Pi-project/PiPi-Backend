package pipi.api.domain.admin.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class ApprovalNotFoundExcpetion extends BusinessException {
    public ApprovalNotFoundExcpetion() {
        super(ErrorCode.APPROVAL_NOT_FOUND);
    }
}
