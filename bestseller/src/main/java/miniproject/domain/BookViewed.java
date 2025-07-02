package miniproject.domain;

import lombok.Data;

@Data
public class BookViewed {

    private Long bookId;
    private Long userId;

    public BookViewed() {}
}
