package pipi.api.domain.user.domain;

import lombok.*;
import pipi.api.domain.post.domain.Post;
import pipi.api.domain.user.domain.enums.Admin;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User {
    @Id
    @Column(length = 50)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 20)
    private String nickname;

    @Column(length = 50)
    private String profileImage;

    @Column(length = 100)
    private String giturl;

    @Column(length = 200)
    private String introduce;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Admin admin;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<UserSkillset> skillsets;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<Post> posts;

    public User setProfile(String profileImage, String giturl, String introduce) {
        this.profileImage = profileImage;
        this.giturl = giturl;
        this.introduce = introduce;

        return this;
    }

    public User changePassword(String password) {
        this.password = password;

        return this;
    }
}
