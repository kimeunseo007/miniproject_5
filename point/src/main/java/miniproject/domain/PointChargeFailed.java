package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointChargeFailed extends AbstractEvent {

    private Long userId;
    private Integer amount;

    public PointChargeFailed(Point aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.amount = aggregate.getAmount();
    }

    public PointChargeFailed() {
        super();
    }
}
//>>> DDD / Domain Event
