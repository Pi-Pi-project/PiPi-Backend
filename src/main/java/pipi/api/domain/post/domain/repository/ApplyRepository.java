package pipi.api.domain.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipi.api.domain.post.domain.Apply;
import pipi.api.domain.post.domain.ApplyPK;

public interface ApplyRepository extends JpaRepository<Apply, ApplyPK> {
}
