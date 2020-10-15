package pipi.api.domain.post.domain;

import lombok.*;
import pipi.api.domain.post.domain.enums.Category;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String idea;

    @Column(length = 50)
    private String img;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String userEmail;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private Category category;
}
