package miniproject.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import miniproject.BestsellerApplication;

@Entity
@Table(name = "BestSeller_table")
@Data
public class BestSeller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bestsellerId;

    private Long bookId; // 책 ID
    private Integer viewCount; // 조회수
    private String selectedStatus; // 베스트셀러 여부
    private Date selectedAt; // 선정일시

    public static BestSellerRepository repository() {
        return BestsellerApplication.applicationContext.getBean(BestSellerRepository.class);
    }

    // 🔁 공통 처리 로직
    private static void processViewCount(Long bookId) {
        repository().findByBookId(bookId).ifPresentOrElse(
            bestseller -> {
                int current = bestseller.getViewCount() != null ? bestseller.getViewCount() : 0;
                bestseller.setViewCount(current + 1);

                if (bestseller.getViewCount() >= 5 && bestseller.getSelectedStatus() == null) {
                    bestseller.setSelectedStatus("베스트셀러");
                    bestseller.setSelectedAt(new Date());

                    BestsellerSelected selected = new BestsellerSelected(bestseller);
                    selected.publishAfterCommit();
                }

                repository().save(bestseller);
            },
            () -> {
                BestSeller newOne = new BestSeller();
                newOne.setBookId(bookId);
                newOne.setViewCount(1);
                repository().save(newOne);
            }
        );
    }

    public static void handleEvent(Long bookId) {
        processViewCount(bookId); // 조회수 처리 및 베스트셀러 선정
    }

    public static void viewCount(BookAccessGranted event) {
        handleEvent(event.getBookId());
    }

    public static void viewCount(PointDeducted event) {
        handleEvent(event.getBookId());
    }

    public static void viewCount(BookViewed event) {
        handleEvent(event.getBookId());
    }
}
