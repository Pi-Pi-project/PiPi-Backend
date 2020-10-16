package pipi.api.domain.user.domain;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserSkillsetPK.class)
public class UserSkillset implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 50)
    private String userEmail;

    @Id
    @Column(length = 50)
    private String skill;
}
