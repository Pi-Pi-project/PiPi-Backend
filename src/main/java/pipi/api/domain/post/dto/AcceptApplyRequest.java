package pipi.api.domain.post.dto;

import lombok.Getter;

@Getter
public class AcceptApplyRequest {
    private Long postId;
    private String userEmail;
}
