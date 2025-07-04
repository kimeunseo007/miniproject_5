package miniproject.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;
import miniproject.UserApplication;

@Entity
@Table(name = "User_table")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String email;
    private String nickname;
    private String passwordHash;

    private Boolean isWriter = false;
    private String subscriptionStatus = "none"; // none, subscribed
    private Integer point = 0;
    private LocalDateTime joinedAt;

    public static UserRepository repository() {
        return UserApplication.applicationContext.getBean(UserRepository.class);
    }

    // 회원가입
    public void register(RegisterCommand command) {
        this.email = command.getEmail();
        this.nickname = command.getNickname();
        this.passwordHash = command.getPasswordHash();
        this.joinedAt = LocalDateTime.now();

        Registered event = new Registered(this);
        event.publishAfterCommit();
    }

    // 작가 요청 (승인되기 전까진 isWriter false)
    public void writerQuest(WriterQuestCommand command) {
        WriterRequested event = new WriterRequested(this);
        event.publishAfterCommit();
    }

    // 🔧 [추가] writerRequest 메서드 (WriterRequestCommand 처리용)
    public void writerRequest(WriterRequestCommand command) {
        WriterRequested event = new WriterRequested(this);
        event.publishAfterCommit();
    }

    // 작가 승인 처리
    public void approveWriter() {
        this.isWriter = true;
    }

    // 구독 요청
    public void subscribe(SubscribeCommand command) {
        this.subscriptionStatus = "subscribed";

        SubscriptionRequested event = new SubscriptionRequested(this);
        event.publishAfterCommit();
    }

    // 구독 취소 요청
    public void cancelSubscription(CancelSubscriptionCommand command) {
        this.subscriptionStatus = "none";

        SubscriptionCancelRequested event = new SubscriptionCancelRequested(this);
        event.publishAfterCommit();
    }

    // 포인트 충전 요청
    public void chargePoint(ChargePointCommand command) {
        this.point += command.getAmount();

        PointChargeRequested event = new PointChargeRequested(this);
        event.publishAfterCommit();
    }
}
