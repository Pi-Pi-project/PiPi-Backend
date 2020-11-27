package pipi.api.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetRoomsResponse {
    private Long id;
    private String title;
    private String coverImg;
}
