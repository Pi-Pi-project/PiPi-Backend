package pipi.api.domain.user.domain;

import lombok.*;
import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@IdClass(ReportPK.class)
public class Report {
    @Id
    @Column(length = 50)
    private String reportedEmail;

    @Id
    @Column(length = 50)
    private String reporterEmail;

    @Column(length = 500)
    private String reason;
}
