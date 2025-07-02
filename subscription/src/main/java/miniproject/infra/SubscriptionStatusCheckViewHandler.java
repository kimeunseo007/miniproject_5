package miniproject.infra;

import java.util.Optional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionStatusCheckViewHandler {

    @Autowired
    private SubscriptionStatusCheckRepository subscriptionStatusCheckRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionRegistered_then_CREATE_1(
        @Payload SubscriptionRegistered subscriptionRegistered
    ) {
        try {
            if (!subscriptionRegistered.validate()) return;

            SubscriptionStatusCheck subscriptionStatusCheck = new SubscriptionStatusCheck();
            subscriptionStatusCheck.setUserId(subscriptionRegistered.getUserId());
            subscriptionStatusCheck.setSubscriptionStatus("ACTIVE");
            subscriptionStatusCheck.setSubscriptionExpireDate(
                String.valueOf(subscriptionRegistered.getSubscriptionExpiryDate())
            );
            subscriptionStatusCheckRepository.save(subscriptionStatusCheck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionCanceled_then_UPDATE_1(
        @Payload SubscriptionCanceled subscriptionCanceled
    ) {
        try {
            if (!subscriptionCanceled.validate()) return;

            Optional<SubscriptionStatusCheck> subscriptionStatusCheckOptional =
                subscriptionStatusCheckRepository.findByUserId(subscriptionCanceled.getUserId());

            if (subscriptionStatusCheckOptional.isPresent()) {
                SubscriptionStatusCheck subscriptionStatusCheck = subscriptionStatusCheckOptional.get();
                subscriptionStatusCheck.setSubscriptionStatus("CANCELLED");
                subscriptionStatusCheckRepository.save(subscriptionStatusCheck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
