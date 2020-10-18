package pipi.api.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SetProfileRequest {
    private MultipartFile profileImg;
    private String giturl;
    private String introduce;
    private String[] skills;
}
