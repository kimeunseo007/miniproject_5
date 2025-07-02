package miniproject.infra;

import javax.transaction.Transactional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

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
        System.out.println(
            "\n\n##### listener Subscribe : " + subscriptionRequested + "\n\n"
        );
        Subscription.subscribe(subscriptionRequested);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookViewed'"
    )
    public void wheneverBookViewed_CheckSubscription(
        @Payload BookViewed bookViewed
    ) {
        System.out.println(
            "\n\n##### listener CheckSubscription : " + bookViewed + "\n\n"
        );
        Subscription.checkSubscription(bookViewed);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionCancelRequested'"
    )
    public void wheneverSubscriptionCancelRequested_SubscriptionCancel(
        @Payload SubscriptionCancelRequested subscriptionCancelRequested
    ) {
        System.out.println(
            "\n\n##### listener SubscriptionCancel : " +
            subscriptionCancelRequested +
            "\n\n"
        );
        Subscription.subscriptionCancel(subscriptionCancelRequested);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeducted'"
    )
    public void wheneverPointDeducted_CheckSubscription(
        @Payload PointDeducted pointDeducted
    ) {
        System.out.println(
            "\n\n##### listener CheckSubscription (Point) : " +
            pointDeducted +
            "\n\n"
        );
        Subscription.checkSubscription(pointDeducted);
    }
}
