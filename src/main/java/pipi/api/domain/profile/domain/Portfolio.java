package pipi.api.domain.profile.domain;

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
public class Portfolio {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String userEmail;

    @Column(length = 50)
    private String title;

    @Column(length = 50)
    private String giturl;

    @Column(length = 1000)
    private String introduce;
}
