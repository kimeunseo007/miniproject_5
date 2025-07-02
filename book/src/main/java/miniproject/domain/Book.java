package miniproject.domain;

import java.util.Optional;
import javax.persistence.*;
import lombok.Data;
import miniproject.BookApplication;

@Entity
@Table(name = "Book_table")
@Data
public class Book {

    public static final String STATUS_WRITTEN = "작성됨";
    public static final String STATUS_DELETED = "삭제됨";
    public static final String STATUS_PUBLISH_REQUESTED = "출간요청";
    public static final String STATUS_PUBLISHED = "출간완료";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;
    private String title;
    private String content;
    private String writerNickname;
    private Long writerId;
    private String coverUrl;
    private String status;
    private Integer viewCount = 0;

    public static BookRepository repository() {
        return BookApplication.applicationContext.getBean(BookRepository.class);
    }

    // 책 작성
    public void write(WriteCommand command) {
        this.title = command.getTitle();
        this.content = command.getContent();
        this.writerId = command.getWriterId();
        this.writerNickname = command.getWriterNickname();
        this.status = Book.STATUS_WRITTEN;

        Written written = new Written(this);
        written.publishAfterCommit();
    }

    // 책 수정
    public void update(UpdateCommand command) {
        this.title = command.getTitle();
        this.content = command.getContent();

        Updated updated = new Updated(this);
        updated.publishAfterCommit();
    }

    // 책 삭제
    public void delete(DeleteCommand command) {
        this.status = Book.STATUS_DELETED;

        Deleted deleted = new Deleted(this);
        deleted.publishAfterCommit();
    }

    // 출간 요청
    public void publishRequest(PublishRequestCommand command) {
        this.status = Book.STATUS_PUBLISHED;

        PublishRequested requested = new PublishRequested(this);
        requested.publishAfterCommit();
    }

    // 표지 선택
    public void selectBookCover(SelectBookCoverCommand command) {
        this.coverUrl = command.getCoverUrl();

        BookCoverSelected selected = new BookCoverSelected(this);
        selected.publishAfterCommit();
    }

    // 책 조회 기록
    public void viewBook(ViewBookCommand command) {
        this.viewCount++; // ✅ 조회수 증가

        BookViewed viewed = new BookViewed(this);
        viewed.setUserId(command.getUserId()); // ✅ userId 세팅
        viewed.publishAfterCommit();
    }

    // AI 표지 요청
    public void requestCoverGeneration(RequestCoverGenerationCommand command) {
        CoverGenerationRequested requested = new CoverGenerationRequested(this);
        requested.publishAfterCommit();
    }

    // 출간 승인됨 → 상태 변경
    public static void publishComplete(PubApproved event) {
        repository().findById(event.getBookId()).ifPresent(book -> {
            book.setStatus("출간완료");
            repository().save(book);

            PublishCompleted completed = new PublishCompleted(book);
            completed.publishAfterCommit();
        });
    }

    // 표지 생성 완료됨 → coverUrl 반영
    public static void coverCandidatesReady(CoverCreated event) {
        repository().findById(event.getBookId()).ifPresent(book -> {
            book.setCoverUrl(event.getCoverUrl());
            repository().save(book);
        });
    }
}
