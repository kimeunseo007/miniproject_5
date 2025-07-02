package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class Registered extends AbstractEvent {

    private Long userId;
    private String email;
    private String nickname;

    public Registered(User aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.email = aggregate.getEmail();
        this.nickname = aggregate.getNickname();
    }

    public Registered() {
        super();
    }
}
