package miniproject.domain;

import lombok.Data;

@Data
public class PointDeducted {

    private Long userId;
    private Integer amount;
    private Long bookId;

    public PointDeducted() {}
}
