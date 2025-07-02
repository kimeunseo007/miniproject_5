package miniproject.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class OpenAiController {

    @Autowired
    OpenAiRepository openAiRepository;

    @PutMapping("/openAis/{id}/bookcovercreate")
    public OpenAi bookCoverCreate(
        @PathVariable("id") Long id,
        @RequestBody BookCoverCreateCommand command
    ) throws Exception {
        OpenAi.bookCoverCreate(command);
        return openAiRepository.findById(id).orElseThrow();
    }

    @PutMapping("/openAis/{id}/select")
    public OpenAi selectCover(
        @PathVariable("id") Long id,
        @RequestBody CoverSelectCommand command
    ) throws Exception {
        OpenAi openAi = openAiRepository.findById(id).orElseThrow();
        openAi.selectCover(command.getSelectedUrl());
        return openAi;
    }

    @GetMapping("/openAis/{id}")
    public OpenAi getBookCover(@PathVariable Long id) {
        return openAiRepository.findById(id).orElseThrow();
    }
}
