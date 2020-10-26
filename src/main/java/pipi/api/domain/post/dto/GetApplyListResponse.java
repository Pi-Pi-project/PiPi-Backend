package pipi.api.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetApplyListResponse {
    private final String userEmail;
    private final String userImg;
    private final String userNickname;
}
