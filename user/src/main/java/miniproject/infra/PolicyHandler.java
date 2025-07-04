package miniproject.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import miniproject.domain.WriterApproved;
// ✅ domain으로 이동했으므로
import miniproject.domain.WriterRegistered;


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

            // ✅ 작가 승인 이벤트 처리
            if ("WriterApproved".equals(eventType)) {
                WriterApproved event = mapper.readValue(eventString, WriterApproved.class);
                System.out.println("✅ WriterApproved 수신됨 - userId: " + event.getUserId());

                userRepository.findById(event.getUserId()).ifPresent(user -> {
                    user.approveWriter();  // 👈 isWriter = true
                    userRepository.save(user);
                });
            }

            // 기존: 작가 등록 이벤트
            else if ("WriterRegistered".equals(eventType)) {
                WriterRegistered event = mapper.readValue(eventString, WriterRegistered.class);
                System.out.println("✅ 작가 등록됨 - userId: " + event.getUserId());
            }

            // 구독 등록 이벤트
            else if ("SubscriptionRegistered".equals(eventType)) {
                SubscriptionRegistered event = mapper.readValue(eventString, SubscriptionRegistered.class);
                System.out.println("✅ 구독 등록됨 - userId: " + event.getUserId());
            }

            // 열람 허용 이벤트
            else if ("BookAccessGranted".equals(eventType)) {
                BookAccessGranted event = mapper.readValue(eventString, BookAccessGranted.class);
                System.out.println("✅ 열람 허용됨 - userId: " + event.getUserId());
            }

            // 열람 거부 이벤트
            else if ("BookAccessDenied".equals(eventType)) {
                BookAccessDenied event = mapper.readValue(eventString, BookAccessDenied.class);
                System.out.println("⛔ 열람 거부됨 - userId: " + event.getUserId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
