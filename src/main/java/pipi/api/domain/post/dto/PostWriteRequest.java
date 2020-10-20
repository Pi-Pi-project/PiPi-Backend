package pipi.api.domain.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostWriteRequest {
    @NotBlank
    private String title;

    private String category;
    private String[] skills;

    @NotBlank
    private String idea;

    private String content;
    private Integer max;
    private MultipartFile img;
}
