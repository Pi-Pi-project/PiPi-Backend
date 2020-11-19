package pipi.api.domain.user.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class UserSkillsetPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userEmail;
    private String skill;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSkillsetPK userSkillsetPK = (UserSkillsetPK) o;
        return Objects.equals(userEmail, userSkillsetPK.userEmail) &&
                Objects.equals(skill, userSkillsetPK.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, skill);
    }
}
