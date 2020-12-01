package pipi.api.domain.chat.service;

import org.springframework.data.domain.Pageable;
import pipi.api.domain.chat.dto.GetChatsResponse;

import java.util.List;

public interface ChatService {
    List<GetChatsResponse> getChats(Long id, Pageable pageable);
    void individualChat(String email);
}
