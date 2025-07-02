package miniproject.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookAccessGranted {

    private Long userId;
    private Long bookId;

    public boolean validate() {
        return userId != null && bookId != null;
    }
}
