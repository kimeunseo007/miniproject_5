package miniproject.domain;

import lombok.Data;

@Data
public class SubscriptionRegistered {
    private Long userId;
    private String subscriptionStatus;
    private String subscriptionExpiryDate;
}
