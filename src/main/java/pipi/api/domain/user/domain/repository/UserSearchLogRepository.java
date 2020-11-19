package pipi.api.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.user.domain.UserSearchLog;

@Repository
public interface UserSearchLogRepository extends JpaRepository<UserSearchLog, Long> {
}
