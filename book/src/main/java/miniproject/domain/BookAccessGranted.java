package miniproject.domain;

import lombok.Data;

@Data
public class BookAccessGranted {

    private Long userId;
    private Long bookId;

    public BookAccessGranted() {}
}
