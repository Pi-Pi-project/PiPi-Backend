package pipi.api.domain.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.project.domain.Approval;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    void deleteByProjectId(Long projectId);
}
