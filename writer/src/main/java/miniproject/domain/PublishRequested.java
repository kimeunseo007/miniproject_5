package miniproject.domain;

import lombok.Data;
import miniproject.dto.BookInfoDto;

@Data
public class PublishRequested {

    private Long bookId;
    private String title;
    private Long writerId;
    private String content;   // 추가
    private String coverUrl;

    public boolean validate() {
        return bookId != null && title != null && writerId != null;
    }

    public String getContent() {
        return this.content;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }
    
    public PublishRequested(BookInfoDto dto) {
        this.bookId = dto.getBookId();
        this.title = dto.getTitle();
        this.writerId = dto.getWriterId();
    }
}
