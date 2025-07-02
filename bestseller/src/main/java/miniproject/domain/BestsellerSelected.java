package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BestsellerSelected extends AbstractEvent {

    private Long bestsellerId;
    private Long bookId;  // 수정: Long으로 변경
    private Integer viewCount;
    private String selectedStatus;
    private Date selectedAt;
    private String title;
    private String coverUrl;
    private Long writerId;

    public BestsellerSelected(BestSeller aggregate) {
        super(aggregate);
        this.bestsellerId = aggregate.getBestsellerId();
        this.bookId = aggregate.getBookId();
        this.viewCount = aggregate.getViewCount();
        this.selectedStatus = aggregate.getSelectedStatus();
        this.selectedAt = aggregate.getSelectedAt();
        this.title = aggregate.getTitle();
        this.coverUrl = aggregate.getCoverUrl();
        this.writerId = aggregate.getWriterId();
    }

    public BestsellerSelected() {
        super();
    }
}
//>>> DDD / Domain Event
