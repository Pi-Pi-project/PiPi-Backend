package pipi.api.domain.chat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.chat.domain.ChatMember;
import pipi.api.domain.chat.domain.ChatMemberPK;

import java.util.List;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, ChatMemberPK> {
    List<ChatMember> findAllByUserEmail(String userEmail);
    List<ChatMember> findByRoomIdAndUserEmailNot(Long id, String userEmail);
    ChatMember findByRoomIdAndUserEmail(Long id, String email);
}
