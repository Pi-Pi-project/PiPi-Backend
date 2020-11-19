package pipi.api.domain.post.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class ApplyPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long postId;
    private String userEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplyPK applyPK = (ApplyPK) o;
        return Objects.equals(postId, applyPK.postId) &&
                Objects.equals(userEmail, applyPK.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userEmail);
    }
}