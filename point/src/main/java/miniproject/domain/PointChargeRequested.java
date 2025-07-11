package miniproject.domain;

import java.util.*;
import lombok.*;
import miniproject.domain.*;
import miniproject.infra.AbstractEvent;

@Data
@ToString
public class PointChargeRequested extends AbstractEvent {

    private Long userId;
    private Integer amount;
    
}
