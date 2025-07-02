package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class BookViewed extends AbstractEvent {

    private Long bookId;
    private Long userId; // ✅ 추가

    public BookViewed(Book aggregate) {
        super(aggregate);
        this.bookId = aggregate.getBookId(); // getter가 있다고 가정
    }

    public BookViewed() {
        super();
    }
}
