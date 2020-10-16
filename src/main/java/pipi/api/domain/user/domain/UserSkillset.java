package pipi.api.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@Builder
public class UserSkillset {
    @Id
    private String userEmail;

    @Column(length = 50)
    private String skill;
}
