package pipi.api.domain.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.post.domain.PostSkillset;
import pipi.api.domain.post.domain.PostSkillsetPK;

import java.util.List;

@Repository
public interface PostSkillsetRepository extends JpaRepository<PostSkillset, PostSkillsetPK> {
    List<PostSkillset> findAllByPostId(Long postId);
}
