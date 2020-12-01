package pipi.api.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IndividualChatResponse {
    private final Long roomId;
}
