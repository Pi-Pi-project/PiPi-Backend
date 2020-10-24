package pipi.api.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import pipi.api.domain.post.domain.PostSkillset;

import java.util.List;

@Getter
@Builder
public class GetPostsResponse {
    private final Long id;
    private final String title;
    private final String img;
    private final String category;
    private final String idea;
    private final List<PostSkillset> postSkillsets;
    private final Integer max;
    private final String userEmail;
    private final String userImg;
    private final String userNickname;
    private final String createdAt;
}
