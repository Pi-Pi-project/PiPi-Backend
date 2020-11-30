package pipi.api.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pipi.api.domain.chat.dto.GetChatsResponse;
import pipi.api.domain.chat.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/{id}")
    public List<GetChatsResponse> getChats(@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable) {
        return chatService.getChats(id, pageable);
    }
}
