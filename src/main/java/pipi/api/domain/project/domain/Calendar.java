package pipi.api.domain.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pipi.api.domain.project.domain.enums.TodoStatus;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long projectId;

    @Column(length = 50)
    private String userNickname;

    @Column(length = 200)
    private String todo;

    @Column(length = 20)
    private String date;

    @Column
    @Enumerated(EnumType.STRING)
    private TodoStatus todoStatus;
}
