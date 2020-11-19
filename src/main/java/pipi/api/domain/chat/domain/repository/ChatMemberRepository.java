package pipi.api.domain.chat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipi.api.domain.chat.domain.ChatMember;
import pipi.api.domain.chat.domain.ChatMemberPK;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, ChatMemberPK> {
}
