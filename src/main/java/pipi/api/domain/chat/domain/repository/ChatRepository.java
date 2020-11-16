package pipi.api.domain.chat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.chat.domain.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
