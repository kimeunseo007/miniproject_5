package miniproject.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "openai", url = "https://api.openai.com/v1/images/generations")
public interface CoverGenerationService {

    @PostMapping(consumes = "application/json")
    Map<String, Object> generateCover(@RequestHeader("Authorization") String authorization,
                                      @RequestBody Map<String, Object> request);
}
