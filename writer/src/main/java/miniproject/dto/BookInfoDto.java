package miniproject.dto;

import lombok.Data;

@Data
public class BookInfoDto {
    private Long bookId;
    private String title;
    private Long writerId;
}
