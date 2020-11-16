package pipi.api.domain.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ChatMemberPK.class)
public class ChatMember {
    @Id
    @Column
    private Long roomId;

    @Id
    @Column(length = 50)
    private String userEmail;
}
