// user/src/main/java/miniproject/domain/WriterApproved.java
package miniproject.domain;

import lombok.Data;

@Data
public class WriterApproved {
    private Long userId;
    private String approvalStatus;
}
