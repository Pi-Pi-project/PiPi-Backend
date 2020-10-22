package pipi.api.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordChangeRequest {
    @NotBlank
    private String password;
}
