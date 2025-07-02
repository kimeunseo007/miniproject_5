package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class SubscriptionCancelRequested extends AbstractEvent {

    private Long userId;
    private Date requestDate;  // 요청 시각 추가

    public SubscriptionCancelRequested() {
        super();
        this.requestDate = new Date();  // 요청 시각 자동 설정
    }

    public SubscriptionCancelRequested(Long userId) {
        super();
        this.userId = userId;
        this.requestDate = new Date();  // 요청 시각 자동 설정
    }
}
