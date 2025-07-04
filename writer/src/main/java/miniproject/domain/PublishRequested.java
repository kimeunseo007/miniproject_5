package miniproject.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class PublishRequested {

    private Long bookId;
    private String title;
    private Long writerId;
    private String content;
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

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
    }
}
