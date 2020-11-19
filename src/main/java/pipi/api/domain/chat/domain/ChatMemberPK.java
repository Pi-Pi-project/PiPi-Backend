package pipi.api.domain.chat.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class ChatMemberPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long roomId;
    private String userEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMemberPK chatMemberPK = (ChatMemberPK) o;
        return Objects.equals(roomId, chatMemberPK.roomId) &&
                Objects.equals(userEmail, chatMemberPK.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, userEmail);
    }
}
