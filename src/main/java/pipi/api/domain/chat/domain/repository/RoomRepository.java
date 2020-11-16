package pipi.api.domain.chat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.chat.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
