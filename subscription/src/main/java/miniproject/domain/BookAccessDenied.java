package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class BookAccessDenied extends AbstractEvent {

    private Long userId;

    public BookAccessDenied(Subscription aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
    }

    public BookAccessDenied() {
        super();
    }
}
