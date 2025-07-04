package miniproject.domain;

import lombok.Data;
import lombok.ToString;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class WriterRequested extends AbstractEvent {

    private Long userId;
    private String email;
    private String nickname;

    public WriterRequested(User aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.email = aggregate.getEmail();
        this.nickname = aggregate.getNickname();
    }

    public WriterRequested() {
        super();
    }
}
