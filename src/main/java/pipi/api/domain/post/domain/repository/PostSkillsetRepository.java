package pipi.api.domain.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipi.api.domain.post.domain.PostSkillset;
import pipi.api.domain.post.domain.PostSkillsetPK;

public interface PostSkillsetRepository extends JpaRepository<PostSkillset, PostSkillsetPK> {
}
