package pipi.api.domain.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pipi.api.domain.post.domain.enums.Accept;

import javax.persistence.*;
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

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Accept accept;

    public Apply setApply(Accept accept) {
        this.accept = accept;

        return this;
    }
}
