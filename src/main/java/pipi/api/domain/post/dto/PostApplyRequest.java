package pipi.api.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class PostApplyRequest {
    @NotNull
    private Long id;
}
