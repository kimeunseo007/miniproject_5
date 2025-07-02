package miniproject.domain;

import lombok.Data;
import miniproject.dto.WriterRequestDto;

@Data
public class WriterRequest {

    private Long userId;
    private String email;
    private String nickname;

    public WriterRequest(WriterRequestDto dto) {
        this.userId = dto.getUserId();
        this.email = dto.getEmail();
        this.nickname = dto.getNickname();
    }
}
