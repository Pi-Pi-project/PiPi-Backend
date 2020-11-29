package pipi.api.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pipi.api.domain.chat.domain.ChatMember;
import pipi.api.domain.chat.domain.Room;
import pipi.api.domain.chat.domain.repository.ChatMemberRepository;
import pipi.api.domain.chat.domain.repository.RoomRepository;
import pipi.api.domain.chat.dto.GetRoomsResponse;
import pipi.api.domain.chat.exception.RoomNotFoundException;
import pipi.api.global.config.security.AuthenticationFacade;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final ChatMemberRepository chatMemberRepository;
    private final AuthenticationFacade authenticationFacade;
    private final RoomRepository roomRepository;

    @Override
    public List<GetRoomsResponse> getRooms() {
        List<ChatMember> members = chatMemberRepository.findAllByUserEmail(authenticationFacade.getUserEmail());
        List<GetRoomsResponse> rooms = new ArrayList<>();
        for (ChatMember member : members) {
            Room room = roomRepository.findById(member.getRoomId())
                    .orElseThrow(RoomNotFoundException::new);
            rooms.add(
                    GetRoomsResponse.builder()
                            .id(room.getId())
                            .title(room.getTitle())
                            .coverImg(room.getCoverImg())
                            .build()
            );
        }
        return rooms;
    }
}
