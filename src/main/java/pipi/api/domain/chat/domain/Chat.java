package pipi.api.domain.chat.domain;

import lombok.*;
import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @Column
    private Long id;

    @Column(length = 500)
    private String message;

    @Column(length = 50)
    private String userEmail;

    @Column
    private Long roomId;
}
