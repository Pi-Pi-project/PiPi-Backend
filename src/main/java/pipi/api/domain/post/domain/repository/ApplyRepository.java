package pipi.api.domain.post.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.post.domain.Apply;
import pipi.api.domain.post.domain.ApplyPK;

import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, ApplyPK> {
    List<Apply> findAllByPostId(Long id);
    Apply findByPostIdAndUserEmail(Long id, String userEmail);
    void deleteByPostIdAndUserEmail(Long id, String userEmail);
    Page<Apply> findAllByUserEmail(Pageable pageable, String userEmail);
}
