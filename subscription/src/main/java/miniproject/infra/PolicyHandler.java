package miniproject.infra;

import javax.transaction.Transactional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionRequested'"
    )
    public void wheneverSubscriptionRequested_Subscribe(
        @Payload SubscriptionRequested subscriptionRequested
    ) {
        System.out.println("\n\n##### listener Subscribe : " + subscriptionRequested + "\n\n");
        Subscription.subscribe(subscriptionRequested);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookViewed'"
    )
    public void wheneverBookViewed_CheckSubscription(
        @Payload BookViewed bookViewed
    ) {
        System.out.println("##### listener CheckSubscription : " + bookViewed);
        Subscription.checkSubscription(bookViewed.getUserId(), bookViewed.getBookId());
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionCancelRequested'"
    )
    public void wheneverSubscriptionCancelRequested_SubscriptionCancel(
        @Payload SubscriptionCancelRequested subscriptionCancelRequested
    ) {
        System.out.println("\n\n##### listener SubscriptionCancel : " + subscriptionCancelRequested + "\n\n");
        Subscription.subscriptionCancel(subscriptionCancelRequested);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPointDeducted_CheckSubscription(@Payload String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(message);

            if ("PointDeducted".equals(json.get("type").asText())) {
                Long userId = json.get("userId").asLong();
                Long bookId = json.get("bookId").asLong();

                System.out.println("##### Kafka: PointDeducted received (userId=" + userId + ", bookId=" + bookId + ")");
                Subscription.checkSubscription(userId, bookId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
