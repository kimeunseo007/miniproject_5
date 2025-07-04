package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class WriterApproved extends AbstractEvent {

    private Long userId;
    private String approvalStatus;

    public WriterApproved(Writer aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();                // ✅ userId로 변경
        this.approvalStatus = aggregate.getApprovalStatus();
    }

    public WriterApproved() {
        super();
    }
}
