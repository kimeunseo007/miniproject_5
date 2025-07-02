package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CoverCreated extends AbstractEvent {

    private Long requestId;
    private Long bookId;
    private String prompt;
    private String coverUrl;

    public CoverCreated(OpenAi aggregate) {
        super(aggregate);
        this.bookId = aggregate.getBookId();
        this.coverUrl = aggregate.getCoverUrl();
        this.requestId = generateRequestId();  // 고유 요청 ID 생성 (예시)
    }

    public CoverCreated() {
        super();
    }
}
//>>> DDD / Domain Event
