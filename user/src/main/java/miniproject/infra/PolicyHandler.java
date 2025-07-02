package miniproject.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    UserRepository userRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onMessage(@Payload String eventString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object raw = mapper.readValue(eventString, Object.class);
            String eventType = mapper.readTree(eventString).get("eventType").asText();

            System.out.println("📥 Kafka 수신: " + eventType);

            // 예시 1: 구독 등록 이벤트 수신 처리
            if ("SubscriptionRegistered".equals(eventType)) {
                SubscriptionRegistered event = mapper.readValue(eventString, SubscriptionRegistered.class);
                System.out.println("✅ 구독 등록됨 - userId: " + event.getUserId());
                // 필요 시 userRepository를 사용해 유저 상태 업데이트 등 가능
            }

            // 예시 2: 열람 허용 이벤트
            else if ("BookAccessGranted".equals(eventType)) {
                BookAccessGranted event = mapper.readValue(eventString, BookAccessGranted.class);
                System.out.println("✅ 열람 허용됨 - userId: " + event.getUserId());
            }

            // 예시 3: 열람 거부 이벤트
            else if ("BookAccessDenied".equals(eventType)) {
                BookAccessDenied event = mapper.readValue(eventString, BookAccessDenied.class);
                System.out.println("⛔ 열람 거부됨 - userId: " + event.getUserId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
