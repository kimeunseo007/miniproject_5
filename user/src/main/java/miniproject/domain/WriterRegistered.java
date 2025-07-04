// 파일명: WriterRegistered.java
package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class WriterRegistered extends AbstractEvent {

    private Long userId;
    private String email;
    private String nickname;

    public WriterRegistered(User aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.email = aggregate.getEmail();
        this.nickname = aggregate.getNickname();
    }

    public WriterRegistered() {
        super();
    }
}
