package pipi.api.domain.user.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class ReportPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reportedEmail;
    private String reporterEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportPK reportPK = (ReportPK) o;
        return Objects.equals(reportedEmail, reportPK.reportedEmail) &&
                Objects.equals(reporterEmail, reportPK.reporterEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportedEmail, reporterEmail);
    }
}
