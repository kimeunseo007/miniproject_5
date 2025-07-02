package miniproject.domain;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import miniproject.PointApplication;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private Integer amount;
    private Long bookId;

    public Long getBookId() {
        return bookId;
    }


    public static PointRepository repository() {
        return PointApplication.applicationContext.getBean(PointRepository.class);
    }

    // 포인트 차감 처리
    public void deductPoint(DeductPointCommand deductPointCommand) {
        if (this.amount >= deductPointCommand.getAmount()) {
            this.amount -= deductPointCommand.getAmount();
            PointDeducted pointDeducted = new PointDeducted(this);
            pointDeducted.setBookId(deductPointCommand.getBookId());
            pointDeducted.setAmount(deductPointCommand.getAmount());
            pointDeducted.publishAfterCommit();
        } else {
            PointDeductFailed pointDeductFailed = new PointDeductFailed(this);
            pointDeductFailed.publishAfterCommit();
        }
    }

    // 포인트 충전 처리
    public void chargePoint(ChargePointCommand chargePointCommand) {
        if (chargePointCommand.getAmount() > 0) {
            this.amount += chargePointCommand.getAmount();
            PointCharged pointCharged = new PointCharged(this);
            pointCharged.publishAfterCommit();
        } else {
            PointChargeFailed pointChargeFailed = new PointChargeFailed(this);
            pointChargeFailed.publishAfterCommit();
        }
    }

    // 포인트 기반 책 열람 허용 판단
    public static void checkSubscription(PointDeducted event) {
        Long userId = event.getUserId();
        Integer requestedAmount = event.getAmount();

        // 포인트가 충분한지 여부 확인하는 메서드로 분리
        if (hasSufficientPoints(userId, requestedAmount)) {
            BookAccessGranted granted = new BookAccessGranted();
            granted.setUserId(userId);
            granted.setBookId(event.getBookId());
            granted.publishAfterCommit();
        } else {
            BookAccessDenied denied = new BookAccessDenied();
            denied.setUserId(userId);
            denied.setBookId(event.getBookId());
            denied.publishAfterCommit();
        }
    }

    private static boolean hasSufficientPoints(Long userId, Integer requestedAmount) {
        return repository().findById(userId)
            .map(point -> point.getAmount() != null && point.getAmount() >= requestedAmount)
            .orElse(false);
    }

    // ✅ BookAccessDenied 이벤트 수신 → 포인트 차감 시도
    public static void checkPoint(BookAccessDenied event) {
        Long userId = event.getUserId();
        Long bookId = event.getBookId();

        repository().findById(userId).ifPresentOrElse(point -> {
            if (point.getAmount() != null && point.getAmount() >= 100) { // 책 한 권당 100포인트
                DeductPointCommand command = new DeductPointCommand();
                command.setUserId(userId);
                command.setAmount(100);
                command.setBookId(bookId);
                point.deductPoint(command);
                repository().save(point);
            } else {
                PointDeductFailed failed = new PointDeductFailed(point);
                failed.publishAfterCommit();
            }
        }, () -> {
            PointDeductFailed failed = new PointDeductFailed();
            failed.setUserId(userId);
            failed.setAmount(0);
            failed.publishAfterCommit();
        });
    }

    // 외부에서 포인트 충전 요청 처리
    public static void chargePoint(PointChargeRequested pointChargeRequested) {
        Long userId = pointChargeRequested.getUserId();
        Integer chargeAmount = pointChargeRequested.getAmount();

        repository().findById(userId).ifPresentOrElse(
            point -> {
                point.setAmount(point.getAmount() + chargeAmount);
                repository().save(point);
                PointCharged event = new PointCharged(point);
                event.publishAfterCommit();
            },
            () -> {
                Point newPoint = new Point();
                newPoint.setUserId(userId);
                newPoint.setAmount(chargeAmount);
                repository().save(newPoint);
                PointCharged event = new PointCharged(newPoint);
                event.publishAfterCommit();
            }
        );
    }
}
