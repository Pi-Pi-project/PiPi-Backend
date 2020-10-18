package pipi.api.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipi.api.domain.user.domain.UserSkillset;
import pipi.api.domain.user.domain.UserSkillsetPK;

public interface UserSkillsetRepository extends JpaRepository<UserSkillset, UserSkillsetPK> {
}
