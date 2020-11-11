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
@IdClass(CalendarPK.class)
public class Calendar {
    @Id
    @Column
    private Long projectId;

    @Id
    @Column(length = 50)
    private String userEmail;

    @Column(length = 200)
    private String todo;

    @Column(length = 20)
    private String day;

    @Column
    @Enumerated(EnumType.STRING)
    private TodoStatus todoStatus;
}
