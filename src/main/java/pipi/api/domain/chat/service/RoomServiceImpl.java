package pipi.api.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pipi.api.domain.chat.domain.Chat;
import pipi.api.domain.chat.domain.ChatMember;
import pipi.api.domain.chat.domain.Room;
import pipi.api.domain.chat.domain.repository.ChatMemberRepository;
import pipi.api.domain.chat.domain.repository.ChatRepository;
import pipi.api.domain.chat.domain.repository.RoomRepository;
import pipi.api.domain.chat.dto.GetChatsResponse;
import pipi.api.domain.chat.dto.GetRoomsResponse;
import pipi.api.domain.chat.exception.RoomNotFoundException;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.global.config.security.AuthenticationFacade;
import pipi.api.global.error.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final ChatMemberRepository chatMemberRepository;
    private final AuthenticationFacade authenticationFacade;
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

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

    @Override
    public List<GetChatsResponse> getChats(Long id, Pageable pageable) {
        Page<Chat> chats = chatRepository.findAllByRoomIdOrderById(id, pageable);
        List<GetChatsResponse> chatsResponses = new ArrayList<>();
        for (Chat chat : chats) {
            User user = userRepository.findByEmail(chat.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
            if (chat.getUserEmail() == authenticationFacade.getUserEmail()) {
                chatsResponses.add(
                        GetChatsResponse.builder()
                                .userNickname(user.getNickname())
                                .message(chat.getMessage())
                                .isMine(true)
                                .build()
                );
            } else {
                chatsResponses.add(
                        GetChatsResponse.builder()
                                .userNickname(user.getNickname())
                                .message(chat.getMessage())
                                .isMine(false)
                                .build()
                );
            }
        }
        return chatsResponses;
    }
}
