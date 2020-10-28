package pipi.api.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.user.domain.UserViewLog;

@Repository
public interface UserViewLogRepository extends JpaRepository<UserViewLog, Long> {
}
