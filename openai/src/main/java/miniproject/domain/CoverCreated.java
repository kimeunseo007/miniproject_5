package miniproject.domain;

import lombok.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class CoverCreated extends AbstractEvent {
    private Long bookId;
    private String previewUrls;

    public CoverCreated(OpenAi aggregate) {
        super(aggregate);
        this.bookId = aggregate.getBookId();
        this.previewUrls = aggregate.getPreviewUrls();
    }

    public CoverCreated() {
        super();
    }
}
