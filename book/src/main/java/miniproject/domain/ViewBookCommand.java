package miniproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class ViewBookCommand {

    private Long bookId;
    private Long writerId;
    private Long userId;  // ✅ 구독 여부 확인용
}
