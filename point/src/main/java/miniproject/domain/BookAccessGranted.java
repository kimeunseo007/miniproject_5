package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class BookAccessGranted extends AbstractEvent {

    private Long userId;
    private Long bookId;

    public BookAccessGranted(Point aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.bookId = aggregate.getBookId();
    }

    public BookAccessGranted() {
        super();
    }
}
