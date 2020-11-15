package pipi.api.domain.post.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class AcceptApplyRequest {
    @NotNull
    private Long postId;

    @NotBlank
    private String userEmail;
}
