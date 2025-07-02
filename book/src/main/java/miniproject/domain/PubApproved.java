package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class PubApproved extends AbstractEvent {

    private Long bookId;
    private Long writerId;
    private String approvalStatus;
    private String publishStatus;
}
