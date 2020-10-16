package pipi.api.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pipi.api.domain.post.domain.Post;
import pipi.api.domain.user.domain.enums.Admin;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@Builder
public class User {
    @Id
    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Column(columnDefinition = "varchar(50) default 'userDefault.jpg'")
    private String profileImg;

    @Column(length = 100)
    private String gitUrl;

    @Column(length = 200)
    private String introduce;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private Admin admin;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<UserSkillset> userSkillsets;

    public User setImage(String profileImg) {
        this.profileImg = profileImg;

        return this;
    }
}
