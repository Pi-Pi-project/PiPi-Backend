package pipi.api.domain.chat.service;

import org.springframework.data.domain.Pageable;
import pipi.api.domain.chat.dto.GetChatsResponse;
import pipi.api.domain.chat.dto.GetRoomsResponse;

import java.util.List;

public interface RoomService {
    List<GetRoomsResponse> getRooms();
    List<GetChatsResponse> getChats(Long id, Pageable pageable);
}
