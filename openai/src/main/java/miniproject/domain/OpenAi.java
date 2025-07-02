package miniproject.domain;

import javax.persistence.*;
import lombok.Data;
import miniproject.OpenAiApplication;
import miniproject.external.CoverGenerationService;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "OpenAi_table")
@Data
public class OpenAi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;

    @Lob
    private String previewUrls; // 미리보기 이미지 리스트 (JSON 문자열)

    private String selectedUrl; // 사용자가 선택한 최종 표지 URL

    public static OpenAiRepository repository() {
        return OpenAiApplication.applicationContext.getBean(OpenAiRepository.class);
    }

    public static CoverGenerationService coverGenerationService() {
        return OpenAiApplication.applicationContext.getBean(CoverGenerationService.class);
    }

    public static void bookCoverCreate(BookCoverCreateCommand command) {
        OpenAi openAi = new OpenAi();
        openAi.setBookId(command.getBookId());

        String prompt = "책 제목: " + command.getTitle() + ", 내용: " + command.getContent();

        Map<String, Object> request = Map.of(
            "prompt", prompt,
            "n", 3,
            "size", "512x512"
        );

        Map<String, Object> response = coverGenerationService().generateCover(
            "Bearer " + command.getApiKey(),
            request
        );

        List<Map<String, String>> data = (List<Map<String, String>>) response.get("data");
        List<String> urls = data.stream().map(img -> img.get("url")).toList();

        openAi.setPreviewUrls(String.join(",", urls)); // 여러 개를 comma로 저장
        repository().save(openAi);

        CoverCreated coverCreated = new CoverCreated(openAi);
        coverCreated.publishAfterCommit();
    }

    public void selectCover(String url) {
        this.selectedUrl = url;
        repository().save(this);
    }

    public static void coverGenerationRequested(CoverGenerationRequested event) {
        BookCoverCreateCommand command = new BookCoverCreateCommand();
        command.setBookId(event.getBookId());
        command.setTitle(event.getTitle());
        command.setContent(event.getContent());
        command.setApiKey("[API 키는 application-secret.yml에서 불러오기]");

        bookCoverCreate(command); // 기존 로직 재활용
    }
}
