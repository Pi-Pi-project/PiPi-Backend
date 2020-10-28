package pipi.api.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.user.domain.UserSkillset;
import pipi.api.domain.user.domain.UserSkillsetPK;

import java.util.List;

@Repository
public interface UserSkillsetRepository extends JpaRepository<UserSkillset, UserSkillsetPK> {
    List<UserSkillset> findAllByUserEmail(String userEmail);
}
