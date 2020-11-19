package pipi.api.domain.auth.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class UserLoginRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
