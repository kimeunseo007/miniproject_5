package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class CoverGenerationRequested extends AbstractEvent {

    private Long bookId;
    private String title;
    private String content;

    public CoverGenerationRequested() {
        super();
    }

    public CoverGenerationRequested(Long bookId, String title, String content) {
        super();
        this.bookId = bookId;
        this.title = title;
        this.content = content;
    }
}
