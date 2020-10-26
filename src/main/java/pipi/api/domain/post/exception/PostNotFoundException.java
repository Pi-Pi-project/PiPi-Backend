package pipi.api.domain.post.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
