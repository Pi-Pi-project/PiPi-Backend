package pipi.api.domain.post.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class PostSkillsetPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long postId;
    private String skill;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostSkillsetPK postSkillsetPK = (PostSkillsetPK) o;
        return Objects.equals(postId, postSkillsetPK.postId) &&
                Objects.equals(skill, postSkillsetPK.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, skill);
    }
}