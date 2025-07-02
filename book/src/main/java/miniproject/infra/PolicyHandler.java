package miniproject.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import miniproject.config.kafka.KafkaProcessor;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    BookRepository bookRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PubApproved'"
    )
    public void wheneverPubApproved_PublishComplete(
        @Payload PubApproved pubApproved
    ) {
        PubApproved event = pubApproved;
        System.out.println(
            "\n\n##### listener PublishComplete : " + pubApproved + "\n\n"
        );

        // Sample Logic //
        Book.publishComplete(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CoverCreated'"
    )
    public void wheneverCoverCreated_CoverCandidatesReady(
        @Payload CoverCreated coverCreated
    ) {
        CoverCreated event = coverCreated;
        System.out.println(
            "\n\n##### listener CoverCandidatesReady : " + coverCreated + "\n\n"
        );

        // Sample Logic //
        Book.coverCandidatesReady(event);
    }
    @StreamListener(
    value = KafkaProcessor.INPUT,
    condition = "headers['type']=='BookAccessGranted'"
    )
    public void wheneverBookAccessGranted_ViewBook(@Payload BookAccessGranted bookAccessGranted) {
        BookAccessGranted event = bookAccessGranted;
        System.out.println("\n\n##### listener ViewBook : " + event + "\n\n");

        bookRepository.findById(event.getBookId()).ifPresent(book -> {
            ViewBookCommand command = new ViewBookCommand();
            command.setBookId(event.getBookId());
            command.setWriterId(book.getWriterId());
            book.viewBook(command);
            bookRepository.save(book);
        });
    }
}
//>>> Clean Arch / Inbound Adaptor
