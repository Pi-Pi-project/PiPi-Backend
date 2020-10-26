package pipi.api.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptApplyRequest {
    private Long id;
    private String userEmail;
}
