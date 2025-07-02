package miniproject.domain;

import javax.persistence.*;
import lombok.Data;
import miniproject.OpenAiApplication;

@Entity
@Table(name = "OpenAi_table")
@Data
public class OpenAi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;
    private String coverUrl;

    public static OpenAiRepository repository() {
        return OpenAiApplication.applicationContext.getBean(OpenAiRepository.class);
    }

    // 📌 AI 표지 생성 요청 처리
    public static void coverGenerationRequested(CoverGenerationRequested event) {
        OpenAi openAi = new OpenAi();
        openAi.setBookId(event.getBookId());

        String coverUrl = coverGenerationService.generateCover(event.getTitle(), event.getContent());
        openAi.setCoverUrl(coverUrl);

        repository().save(openAi);

        CoverCreated coverCreated = new CoverCreated(openAi);
        coverCreated.publishAfterCommit();
    }
}
