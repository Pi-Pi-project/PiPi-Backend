package pipi.api.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class UserReportRequest {
    @Email
    @NotBlank
    private String reportedEmail;

    @NotBlank
    private String reason;
}
