package miniproject.dto;

import lombok.Data;

@Data
public class WriterRequestDto {
    private Long userId;
    private String email;
    private String nickname;
}
