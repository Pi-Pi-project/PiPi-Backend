package pipi.api.domain.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.post.domain.Apply;
import pipi.api.domain.post.domain.ApplyPK;

import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, ApplyPK> {
    List<Apply> findAllByPostId(Long id);
    Apply findByPostIdAndUserEmail(Long id, String userEmail);
}
