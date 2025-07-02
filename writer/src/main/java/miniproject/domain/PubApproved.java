package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class PubApproved extends AbstractEvent {

    private Long writerId;
    private String approvalStatus;
    private String publishStatus;
    private Long bookId;
    private String title;
    private String coverUrl;

    public PubApproved(Writer aggregate) {
        super(aggregate);
        this.writerId = aggregate.getWriterId();               // ✅ writerId 설정
        this.approvalStatus = aggregate.getApprovalStatus();   // ✅ 승인 상태
        this.publishStatus = aggregate.getPublishStatus();     // ✅ 출간 상태
        this.bookId = aggregate.getBookId();   // Writer 객체가 bookId 갖고 있어야 함
        this.title = aggregate.getTitle();
        this.coverUrl = aggregate.getCoverUrl();
    }

    public PubApproved() {
        super();
    }
}
