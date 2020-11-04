package pipi.api.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class EmailSendRequest {
    @Email
    @NotBlank
    private String email;
}
