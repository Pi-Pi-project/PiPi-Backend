package pipi.api.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pipi.api.domain.chat.domain.Chat;
import pipi.api.domain.chat.domain.ChatMember;
import pipi.api.domain.chat.domain.Room;
import pipi.api.domain.chat.domain.enums.RoomStatus;
import pipi.api.domain.chat.domain.repository.ChatMemberRepository;
import pipi.api.domain.chat.domain.repository.ChatRepository;
import pipi.api.domain.chat.domain.repository.RoomRepository;
import pipi.api.domain.chat.dto.GetChatsResponse;
import pipi.api.domain.chat.dto.IndividualChatResponse;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.global.config.security.AuthenticationFacade;
import pipi.api.global.error.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<GetChatsResponse> getChats(Long id, Pageable pageable) {
        Page<Chat> chats = chatRepository.findAllByRoomIdOrderByIdDesc(id, pageable);
        List<GetChatsResponse> chatsResponses = new ArrayList<>();
        for (Chat chat : chats) {
            User user = userRepository.findByEmail(chat.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
            if (chat.getUserEmail().equals(authenticationFacade.getUserEmail())) {
                chatsResponses.add(
                        GetChatsResponse.builder()
                                .userNickname(user.getNickname())
                                .message(chat.getMessage())
                                .profileImg(user.getProfileImage())
                                .isMine(true)
                                .build()
                );
            } else {
                chatsResponses.add(
                        GetChatsResponse.builder()
                                .userNickname(user.getNickname())
                                .message(chat.getMessage())
                                .profileImg(user.getProfileImage())
                                .isMine(false)
                                .build()
                );
            }
        }
        return chatsResponses;
    }

    @Override
    public IndividualChatResponse individualChat(String email) {
        List<ChatMember> members = chatMemberRepository.findAllByUserEmail(authenticationFacade.getUserEmail());
        for (ChatMember member : members) {
            List<Room> rooms = roomRepository.findAllByIdAndRoomStatus(member.getRoomId(), RoomStatus.INDIVIDUAL);
            for (Room room : rooms) {
                ChatMember chatMember = chatMemberRepository.findByRoomId(room.getId());
                if (chatMember != null) {
                    return IndividualChatResponse.builder()
                            .roomId(room.getId())
                            .build();
                }
            }
        }
        Room createdRoom = roomRepository.save(
                Room.builder()
                        .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                        .roomStatus(RoomStatus.INDIVIDUAL)
                        .build()
        );
        chatMemberRepository.save(
                ChatMember.builder()
                        .roomId(createdRoom.getId())
                        .userEmail(authenticationFacade.getUserEmail())
                        .build()
        );
        chatMemberRepository.save(
                ChatMember.builder()
                        .roomId(createdRoom.getId())
                        .userEmail(email)
                        .build()
        );
        return IndividualChatResponse.builder()
                .roomId(createdRoom.getId())
                .build();
    }
}
