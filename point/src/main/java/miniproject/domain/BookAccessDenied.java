package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class BookAccessDenied extends AbstractEvent {

    private Long userId;
    private Long bookId;  // 어떤 책에 대한 접근이 거부되었는지
    private String reason; // 접근 거부 이유
    private Date deniedAt; // 거부된 시간

    public BookAccessDenied() {
        super();
    }

    public BookAccessDenied(Long userId, Long bookId, String reason) {
        super();
        this.userId = userId;
        this.bookId = bookId;
        this.reason = reason;
        this.deniedAt = new Date(); // 현재 시간 기록
    }
}
