package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PubRejected extends AbstractEvent {

    private Long writerId;
    private Long bookId;
    private String publishStatus;

    private String title;
    private String coverUrl;

    public PubRejected(Writer aggregate) {
        super(aggregate);
        this.writerId = aggregate.getWriterId();
        this.publishStatus = aggregate.getPublishStatus();

        // writer가 book 정보를 갖고 있다면 포함
        this.bookId = aggregate.getBookId();
        this.title = aggregate.getTitle();
        this.coverUrl = aggregate.getCoverUrl();
    }

    public PubRejected() {
        super();
    }
}
//>>> DDD / Domain Event
