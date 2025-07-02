package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class PublishRequested extends AbstractEvent {

    private Long bookId;
    private String title;
    private String content;
    private Long writerId;
    private String coverUrl;

    public PublishRequested(Book aggregate) { // 생성자에서 this(Book Aggregate)의 필드를 꺼내서 초기화하는 게 핵심!
        super(aggregate);
        this.bookId = aggregate.getBookId();
        this.title = aggregate.getTitle();
        this.content = aggregate.getContent();
        this.writerId = aggregate.getWriterId();
        this.coverUrl = aggregate.getCoverUrl();
    }

    public PublishRequested() {
        super();
    }
}
