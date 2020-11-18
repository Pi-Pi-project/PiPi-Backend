package pipi.api.domain.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pipi.api.domain.project.domain.enums.ApprovalStatus;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 50)
    private String img;

    @Column(length = 100)
    private String idea;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approval;

    @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL)
    private List<Calendar> calrendars;

    @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL)
    private List<Approval> approvals;

    public Project setApproval(ApprovalStatus approval) {
        this.approval = approval;

        return this;
    }
}
