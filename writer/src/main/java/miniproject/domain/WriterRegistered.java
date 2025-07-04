package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class WriterRegistered extends AbstractEvent {

    private Long writerId;
    private String nickname;
    private Long userId;     // 👈 추가
    private String email;    // 👈 추가

    public WriterRegistered(Writer aggregate) {
        super(aggregate);
        this.writerId = aggregate.getWriterId();
        this.nickname = aggregate.getNickname();  // 👈 여기도 Writer에 getter가 있어야 함
        this.userId = aggregate.getUserId();      // 👈 Writer에 있어야 함
        this.email = aggregate.getEmail();        // 👈 Writer에 있어야 함
    }

    public WriterRegistered() {
        super();
        this.setEventType("WriterRegistered"); 
    }
}
