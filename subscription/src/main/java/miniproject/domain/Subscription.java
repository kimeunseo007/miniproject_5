package miniproject.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import miniproject.SubscriptionApplication;
import org.springframework.transaction.annotation.Transactional;


@Entity
@Table(name = "Subscription_table")
@Data
public class Subscription {

    @Id
    private Long userId;

    private String subscriptionStatus; // SUBSCRIBED / CANCELED
    private Date subscriptionExpiryDate;

    public static SubscriptionRepository repository() {
        return SubscriptionApplication.applicationContext.getBean(SubscriptionRepository.class);
    }

    // 구독 등록
    public void subscriptionRegister(SubscriptionRegisterCommand command) {
        this.subscriptionStatus = "SUBSCRIBED";  // 상태를 "SUBSCRIBED"로 설정
        this.subscriptionExpiryDate = Date.from(
            LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant()  // 만료일을 현재 날짜 + 30일로 설정
        );

        // Subscription 객체를 DB에 저장
        repository().save(this);
        repository().flush(); 

        // 구독 등록 이벤트 발행
        SubscriptionRegistered event = new SubscriptionRegistered(this);
        event.publishAfterCommit();  // 이벤트 발행
    }

    // 구독 취소
    @Transactional
    public void subscriptionCancel(SubscriptionCancelCommand command) {
        this.subscriptionStatus = "CANCELED";
        this.subscriptionExpiryDate = Date.from(
            LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
        );
        repository().save(this);
        repository().flush();

        SubscriptionCanceled event = new SubscriptionCanceled(this);
        event.publishAfterCommit();
    }

    public static void subscriptionCancel(SubscriptionCancelRequested event) {
        repository().findByUserId(event.getUserId()).ifPresent(subscription -> {
            subscription.setSubscriptionStatus("CANCELLED");
            repository().save(subscription);
            repository().flush();
        });
    }

    // 구독 상태 확인 → 책 열람 허용 여부 판단 (BookViewed 이벤트 대응)
    public void checkSubscription(CheckSubscriptionCommand command) {
        Date now = new Date();

        if ("SUBSCRIBED".equals(this.subscriptionStatus) &&
            this.subscriptionExpiryDate != null &&
            this.subscriptionExpiryDate.after(now)) {

            BookAccessGranted granted = new BookAccessGranted(this);
            granted.publishAfterCommit();

        } else {
            BookAccessDenied denied = new BookAccessDenied(this);
            denied.publishAfterCommit();
        }
    }

    // ✅ 포인트 열람 시에도 구독 상태 확인 → 책 열람 허용 여부 판단 (PointDeducted 이벤트 대응)
    // ✅ 다른 서비스에서 넘어온 Kafka 메시지 처리용
    public static void checkSubscription(Long userId, Long bookId) {
        Date now = new Date();

        repository().findByUserId(userId).ifPresentOrElse(subscription -> {
            if ("SUBSCRIBED".equals(subscription.getSubscriptionStatus()) &&
                subscription.getSubscriptionExpiryDate() != null &&
                subscription.getSubscriptionExpiryDate().after(now)) {

                BookAccessGranted granted = new BookAccessGranted();
                granted.setUserId(userId);
                granted.setBookId(bookId);
                granted.publishAfterCommit();

            } else {
                BookAccessDenied denied = new BookAccessDenied();
                denied.setUserId(userId);
                denied.setBookId(bookId);
                denied.publishAfterCommit();
            }
        }, () -> {
            BookAccessDenied denied = new BookAccessDenied();
            denied.setUserId(userId);
            denied.setBookId(bookId);
            denied.publishAfterCommit();
        });
    }

    // public static void checkSubscription(PointDeducted event) {
    //     Long userId = event.getUserId();
    //     Date now = new Date();

    //     repository().findByUserId(userId).ifPresentOrElse(subscription -> {
    //         if ("SUBSCRIBED".equals(subscription.getSubscriptionStatus()) &&
    //             subscription.getSubscriptionExpiryDate() != null &&
    //             subscription.getSubscriptionExpiryDate().after(now)) {

    //             BookAccessGranted granted = new BookAccessGranted();
    //             granted.setUserId(userId);
    //             granted.setBookId(event.getBookId());
    //             granted.publishAfterCommit();

    //         } else {
    //             BookAccessDenied denied = new BookAccessDenied();
    //             denied.setUserId(userId);
    //             denied.setBookId(event.getBookId());
    //             denied.publishAfterCommit();
    //         }
    //     }, () -> {
    //         BookAccessDenied denied = new BookAccessDenied();
    //         denied.setUserId(userId);
    //         denied.setBookId(event.getBookId());
    //         denied.publishAfterCommit();
    //     });
    // }

    // // PointDeducted 이벤트 처리용 구독 상태 확인
    // public static void checkSubscription(PointDeducted event) {
    //     repository().findById(event.getUserId()).ifPresent(subscription -> {
    //         subscription.checkSubscription(new CheckSubscriptionCommand());
    //     });
    // }
    
    // D-Day 계산 (ViewHandler 등에서 사용)
    public long calculateDDay() {
        if (this.subscriptionExpiryDate == null) return Long.MIN_VALUE;
        LocalDate now = LocalDate.now();
        LocalDate expiry = subscriptionExpiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return java.time.temporal.ChronoUnit.DAYS.between(now, expiry);
    }

    public static void subscribe(SubscriptionRequested event) {
        Subscription subscription = new Subscription();
        subscription.setUserId(event.getUserId());
        subscription.setSubscriptionStatus("SUBSCRIBED");
        subscription.setSubscriptionExpiryDate(
            Date.from(LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant())
        );
        repository().save(subscription);
    }

    

}
