package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class PointDeducted extends AbstractEvent {

    private Long userId;
    private Integer amount;
    private Long bookId;
    
    public PointDeducted(Point aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.amount = aggregate.getAmount();
    }

    public PointDeducted() {
        super();
    }
}
