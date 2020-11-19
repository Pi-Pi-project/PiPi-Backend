package pipi.api.domain.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Approval {
    @Id
    @Column
    private Long projectId;

    @Column(length = 50)
    private String giturl;

    @Column(length = 1000)
    private String introduce;
}
