package miniproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniproject.WriterApplication;

@Entity
@Table(name = "Writer_table")
@Data
public class Writer {
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_NOT_REQUESTED = "NOT_REQUESTED";
    public static final String STATUS_PUBLISHED = "PUBLISHED";
    public static final String STATUS_PUB_REJECTED = "REJECTED";
    public static final String STATUS_PUB_REQUESTED = "REQUESTED";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long writerId;
    private Long userId;
    private Long bookId;
    private String nickname;
    private String email;

    private String title;
    private String coverUrl;
    private String approvalStatus; // 승인 상태 (APPROVED, REJECTED 등)
    private String publishStatus;  // 출간 상태 (PUBLISHED, REJECTED 등)

    public static WriterRepository repository() {
        return WriterApplication.applicationContext.getBean(WriterRepository.class);
    }

    // 작가 승인
    public void writerApprove(WriterApproveCommand command) {
        this.approvalStatus = Writer.STATUS_APPROVED;

        WriterApproved event = new WriterApproved(this);
        event.publishAfterCommit();
    }

    // 작가 거절
    public void writerReject(WriterRejectCommand command) {
        this.approvalStatus = Writer.STATUS_REJECTED;

        WriterRejected event = new WriterRejected(this);
        event.publishAfterCommit();
    }

    // 출간 승인
    public void pubApprove(PubApproveCommand command) {
        this.publishStatus = Writer.STATUS_PUBLISHED;

        PubApproved event = new PubApproved(this);
        event.publishAfterCommit();
    }

    // 출간 거절
    public void pubReject(PubRejectCommand command) {
        this.publishStatus = Writer.STATUS_REJECTED;

        PubRejected event = new PubRejected(this);
        event.publishAfterCommit();
    }

    // 작가 등록 요청
    public static void writerRequest(WriterRequest event) {
        Writer writer = new Writer();
        writer.setUserId(event.getUserId());
        writer.setNickname(event.getNickname()); // ✅ 추가
        writer.setEmail(event.getEmail());
        writer.setApprovalStatus(STATUS_PENDING);          // ← 상수 사용
        writer.setPublishStatus(STATUS_NOT_REQUESTED);     // ← 상수 사용
        repository().save(writer);
    }

    // 출간 요청 처리
    public static void publishRequest(PublishRequested event) {
        repository().findById(event.getWriterId()).ifPresent(writer -> {
            writer.setPublishStatus(STATUS_PUB_REQUESTED);  // ← 상수 사용
            repository().save(writer);
        });
    }
}
