package pipi.api.domain.post.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostApplyRequest {
    @NotBlank
    private Long id;
}
