package pipi.api.domain.post.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 100)
    private String idea;

    @Column(length = 50)
    private String img;

    @Column(length = 50)
    private String title;

    @Column(length = 30)
    private String category;

    @Column(length = 50)
    private String userEmail;

    @Column(length = 1000)
    private String content;

    @Column
    private Integer max;

    @Column
    private LocalDateTime createdAt;
}
