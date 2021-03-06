package pipi.api.domain.project.dto;

import lombok.Builder;
import lombok.Getter;
import pipi.api.domain.post.domain.PostSkillset;

import java.util.List;

@Getter
@Builder
public class GetMyProjectResponse {
    private final Long id;
    private final String title;
    private final String img;
    private final String idea;
}
