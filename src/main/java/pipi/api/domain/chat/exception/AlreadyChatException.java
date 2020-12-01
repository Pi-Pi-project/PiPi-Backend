package pipi.api.domain.chat.exception;

import pipi.api.global.error.exception.BusinessException;
import pipi.api.global.error.exception.ErrorCode;

public class AlreadyChatException extends BusinessException {
    public AlreadyChatException() {
        super(ErrorCode.ALREADY_CHAT);
    }
}
