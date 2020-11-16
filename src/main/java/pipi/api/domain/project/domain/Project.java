package pipi.api.domain.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column
    private boolean approval;

    @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL)
    private List<Calendar> calrendars;

    public Project setApproval(boolean approval) {
        this.approval = approval;

        return this;
    }
}
