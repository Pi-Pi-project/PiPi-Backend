package pipi.api.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetChatsResponse {
    private String userNickname;
    private String message;
    private boolean isMine;
}
