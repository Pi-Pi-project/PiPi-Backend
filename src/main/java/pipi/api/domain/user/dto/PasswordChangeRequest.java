package pipi.api.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PasswordChangeRequest {
    @NotBlank
    private String password;
}
