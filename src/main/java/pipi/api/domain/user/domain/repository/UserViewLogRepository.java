package pipi.api.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipi.api.domain.user.domain.UserViewLog;

public interface UserViewLogRepository extends JpaRepository<UserViewLog, Long> {
}
