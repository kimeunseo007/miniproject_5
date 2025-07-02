package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class BookAccessGranted extends AbstractEvent {

    private Long userId;

    public BookAccessGranted(Subscription aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
    }

    public BookAccessGranted() {
        super();
    }
}
