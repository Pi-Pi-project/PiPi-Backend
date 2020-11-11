package pipi.api.domain.project.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class CalendarPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long projectId;
    private String userEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarPK that = (CalendarPK) o;
        return Objects.equals(projectId, that.projectId) &&
                Objects.equals(userEmail, that.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userEmail);
    }
}
