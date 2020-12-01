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
import pipi.api.domain.chat.exception.AlreadyChatException;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.global.config.security.AuthenticationFacade;
import pipi.api.global.error.exception.UserNotFoundException;

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
        Page<Chat> chats = chatRepository.findAllByRoomIdOrderById(id, pageable);
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
    public void individualChat(String email) {
        List<ChatMember> members = chatMemberRepository.findAllByUserEmail(email);
        for (ChatMember member : members) {
            List<Room> room = roomRepository.findAllByIdAndRoomStatus(member.getRoomId(), RoomStatus.INDIVIDUAL);
            if (room != null)
                throw new AlreadyChatException();
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
//        roomRepository.save(
//                Room.builder()
//                        .title()
//                        .build()
//        );
    }
}
