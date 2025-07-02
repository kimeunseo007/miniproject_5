package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class WriterApproved extends AbstractEvent {

    private Long writerId;
    private String approvalStatus;

    public WriterApproved(Writer aggregate) {
        super(aggregate);
        this.writerId = aggregate.getWriterId();          // ✅ writerId 설정
        this.approvalStatus = aggregate.getApprovalStatus();  // ✅ 상태 설정
    }

    public WriterApproved() {
        super();
    }
}
