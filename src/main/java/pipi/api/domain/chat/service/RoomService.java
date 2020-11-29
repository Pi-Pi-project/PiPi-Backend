package pipi.api.domain.chat.service;

import pipi.api.domain.chat.dto.GetRoomsResponse;

import java.util.List;

public interface RoomService {
    List<GetRoomsResponse> getRooms();
}
