package pipi.api.domain.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pipi.api.domain.project.domain.enums.MemberStatus;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MemberPK.class)
public class Member {
    @Id
    @Column
    private Long projectId;

    @Id
    @Column
    private String userEmail;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;
}
