package pipi.api.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipi.api.domain.user.domain.UserSkillset;
import pipi.api.domain.user.domain.UserSkillsetPK;

import java.util.List;

public interface UserSkillsetRepository extends JpaRepository<UserSkillset, UserSkillsetPK> {
    List<UserSkillset> findAllByUserEmail(String userEmail);
}
