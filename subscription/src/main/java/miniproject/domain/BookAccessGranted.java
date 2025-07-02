package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class BookAccessGranted extends AbstractEvent {

    private Long userId;
    private Long bookId;

    public BookAccessGranted(Subscription aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
    }

    public BookAccessGranted() {
        super();
    }
}
