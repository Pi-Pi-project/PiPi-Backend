package pipi.api.domain.user.domain;

import lombok.*;
import pipi.api.domain.chat.domain.Chat;
import pipi.api.domain.post.domain.Apply;
import pipi.api.domain.post.domain.Post;
import pipi.api.domain.profile.domain.Portfolio;
import pipi.api.domain.project.domain.Member;
import pipi.api.domain.project.domain.Calendar;
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

    @OneToOne
    @JoinColumn
    private Portfolio firstPortfolio;

    @OneToOne
    @JoinColumn
    private Portfolio secondPortfolio;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<UserSkillset> skillsets;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<UserViewLog> userViewLogs;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<UserSearchLog> userSearchLogs;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<Apply> applies;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<Portfolio> portfolios;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<Calendar> calendars;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL)
    private List<Chat> chatList;

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

    public User setFirstPortfolio(Portfolio first) {
        this.firstPortfolio = first;

        return this;
    }

    public User setSecondPortfolio(Portfolio second) {
        this.secondPortfolio = second;

        return this;
    }
}
