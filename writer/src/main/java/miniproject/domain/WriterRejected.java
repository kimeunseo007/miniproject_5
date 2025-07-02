package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class WriterRejected extends AbstractEvent {

    private Long writerId;
    private String approvalStatus;

    public WriterRejected(Writer aggregate) {
        super(aggregate);
        this.writerId = aggregate.getWriterId();              // ✅ writerId 설정
        this.approvalStatus = Writer.STATUS_REJECTED;  // ✅ 상태 설정
    }

    public WriterRejected() {
        super();
    }
}
