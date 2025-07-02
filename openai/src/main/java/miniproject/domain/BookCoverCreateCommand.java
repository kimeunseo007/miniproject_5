package miniproject.domain;

import lombok.Data;

@Data
public class BookCoverCreateCommand {
    private Long bookId;
    private String title;
    private String content;
    private String apiKey;
}
