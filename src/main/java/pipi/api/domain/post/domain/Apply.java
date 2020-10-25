package pipi.api.domain.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ApplyPK.class)
public class Apply implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private Long postId;

    @Id
    @Column(length = 50)
    private String userEmail;
}
