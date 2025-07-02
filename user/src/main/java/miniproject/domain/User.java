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

    // 작가 요청
    public void writerQuest(WriterQuestCommand command) {
        this.isWriter = true;

        WriterRequest event = new WriterRequest(this);
        event.publishAfterCommit();
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
        this.point += command.getAmount(); // 단순 누적

        PointChargeRequested event = new PointChargeRequested(this);
        event.publishAfterCommit();
    }
}
