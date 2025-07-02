package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class CoverGenerationRequested extends AbstractEvent {

    private Long bookId;
    private String title;
    private String content;
    private Long userId;  // 추가: 요청한 사용자 ID
    private Date requestDate; // 추가: 요청 시각

    public CoverGenerationRequested() {
        super();
        this.requestDate = new Date();  // 요청 시각 자동 설정
    }

    public CoverGenerationRequested(Long bookId, String title, String content, Long userId, String reason) {
        super();
        this.bookId = bookId;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.requestDate = new Date();
    }
}
