package miniproject.infra;

import lombok.Data;
import miniproject.domain.PublishRequested;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publish-requests")
public class PublishRequestController {

    @PostMapping
    public void requestPublish(@RequestBody PublishRequestDto dto) {
        System.out.println("📢 /publish-requests called with: " + dto);

        PublishRequested event = new PublishRequested();
        event.setWriterId(dto.getWriterId());
        event.setTitle(dto.getTitle());
        event.setCoverUrl(dto.getCoverUrl());

        event.publish();
    }

    @Data
    public static class PublishRequestDto {
        private Long writerId;
        private String title;
        private String coverUrl;
    }
}
