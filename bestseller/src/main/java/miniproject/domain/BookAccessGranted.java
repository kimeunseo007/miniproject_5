package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class BookAccessGranted extends AbstractEvent {

    private Long userId;
    private Long bookId;

    public BookAccessGranted() {
        super();
    }

    public BookAccessGranted(Long userId, Long bookId) {
        super();
        this.userId = userId;
        this.bookId = bookId;
    }
}
