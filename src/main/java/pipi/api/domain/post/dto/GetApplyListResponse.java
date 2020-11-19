package pipi.api.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import pipi.api.domain.post.domain.enums.Accept;

@Getter
@Builder
public class GetApplyListResponse {
    private final String userEmail;
    private final String userImg;
    private final String userNickname;
    private final Enum<Accept> status;
}
