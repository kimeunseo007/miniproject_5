package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class WriterRequest extends AbstractEvent {

    private Long userId;

    public WriterRequest(User aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
    }

    public WriterRequest() {
        super();
    }
}


