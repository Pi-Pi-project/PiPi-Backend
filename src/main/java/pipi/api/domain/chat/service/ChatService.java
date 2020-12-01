package pipi.api.domain.chat.service;

import org.springframework.data.domain.Pageable;
import pipi.api.domain.chat.dto.GetChatsResponse;
import pipi.api.domain.chat.dto.IndividualChatResponse;

import java.util.List;

public interface ChatService {
    List<GetChatsResponse> getChats(Long id, Pageable pageable);
    IndividualChatResponse individualChat(String email);
}
