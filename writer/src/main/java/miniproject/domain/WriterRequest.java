package miniproject.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import miniproject.domain.WriterRequestDto;
import miniproject.infra.AbstractEvent;

@Data
@ToString
@NoArgsConstructor
public class WriterRequest extends AbstractEvent {

    private Long userId;
    private String email;
    private String nickname;

    public WriterRequest(WriterRequestDto dto) {
        super();
        this.userId = dto.getUserId();
        this.email = dto.getEmail();
        this.nickname = dto.getNickname();
    }
}
