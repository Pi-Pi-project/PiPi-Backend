package pipi.api.domain.project.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.project.domain.Member;
import pipi.api.domain.project.domain.MemberPK;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberPK> {
    Page<Member> findAllByUserEmail(String userEmail, Pageable pageable);
    Member findByUserEmailAndProjectId(String userEmail, Long projectId);
    List<Member> findAllByProjectId(Long ProjectId);
}
