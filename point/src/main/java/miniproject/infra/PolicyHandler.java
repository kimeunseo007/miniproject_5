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
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 📌 BookAccessDenied 이벤트 수신 → checkPoint() 실행
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookAccessDenied'"
    )
    public void wheneverBookAccessDenied_CheckPoint(
        @Payload BookAccessDenied bookAccessDenied
    ) {
        System.out.println("##### listener CheckPoint : " + bookAccessDenied);
        Point.checkPoint(bookAccessDenied);
    }

    // 📌 외부 포인트 충전 요청 이벤트 수신 → chargePoint() 실행
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointChargeRequested'"
    )
    public void wheneverPointChargeRequested_ChargePoint(
        @Payload PointChargeRequested pointChargeRequested
    ) {
        System.out.println("##### listener ChargePoint : " + pointChargeRequested);
        Point.chargePoint(pointChargeRequested);
    }
}
