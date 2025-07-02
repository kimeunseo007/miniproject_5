package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionCancelRequested extends AbstractEvent {

    private Long userId;

    public SubscriptionCancelRequested(User aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();  // 필드 초기화 추가!
    }
}
//>>> DDD / Domain Event
