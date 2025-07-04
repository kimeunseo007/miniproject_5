package miniproject.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    WriterRepository writerRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {
        // 기본 디버깅용 수신 로그
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='WriterRequest'"
    )
    public void handleWriterRequest(@Payload WriterRequest event) {
        if (!event.validate()) return;

        System.out.println("##### listener WriterRequest : " + event.toJson());

        // Writer 생성 및 저장
        Writer.writerRequest(event);

        // WriterRegistered 이벤트 발행
        WriterRegistered writerRegistered = new WriterRegistered();
        writerRegistered.setUserId(event.getUserId());
        writerRegistered.setEmail(event.getEmail());
        writerRegistered.setNickname(event.getNickname());
        writerRegistered.publishAfterCommit();

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PublishRequested'"
    )
    public void wheneverPublishRequested_PublishRequest(@Payload PublishRequested publishRequested) {
        if (!publishRequested.validate()) return;

        System.out.println("##### listener PublishRequest : " + publishRequested.toJson());

        // Writer 출간 요청 상태로 변경
        Writer.publishRequest(publishRequested);
    }
}
