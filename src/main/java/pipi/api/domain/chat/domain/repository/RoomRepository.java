package pipi.api.domain.chat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.chat.domain.Room;
import pipi.api.domain.chat.domain.enums.RoomStatus;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByIdAndRoomStatus(Long id, RoomStatus roomStatus);
}
