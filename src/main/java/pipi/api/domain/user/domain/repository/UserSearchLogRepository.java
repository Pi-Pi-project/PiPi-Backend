package pipi.api.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipi.api.domain.user.domain.UserSearchLog;

public interface UserSearchLogRepository extends JpaRepository<UserSearchLog, Long> {
}
