package pipi.api.domain.post.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PostSkillsetPK.class)
public class PostSkillset implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private Long postId;

    @Id
    @Column(length = 50)
    private String skill;
}

