package pipi.api.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipi.api.domain.user.domain.UserSkillset;

public interface UserSkillsetRepository extends JpaRepository<UserSkillset, String> {
}
