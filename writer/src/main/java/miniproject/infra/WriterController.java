package miniproject.infra;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/writers") // 상위 경로
@Transactional
public class WriterController {

    @Autowired
    WriterRepository writerRepository;

    @GetMapping
    public List<Writer> getAllWriters() {
        return (List<Writer>) writerRepository.findAll();
    }

    @PutMapping("/{id}/writerapprove")
    public Writer writerApprove(
        @PathVariable Long id,
        @RequestBody WriterApproveCommand writerApproveCommand
    ) throws Exception {
        Optional<Writer> optionalWriter = writerRepository.findById(id);
        optionalWriter.orElseThrow(() -> new Exception("No Entity Found"));

        Writer writer = optionalWriter.get();
        writer.writerApprove(writerApproveCommand);
        return writerRepository.save(writer);
    }

    @PutMapping("/{id}/writerreject")
    public Writer writerReject(
        @PathVariable Long id,
        @RequestBody WriterRejectCommand writerRejectCommand
    ) throws Exception {
        Optional<Writer> optionalWriter = writerRepository.findById(id);
        optionalWriter.orElseThrow(() -> new Exception("No Entity Found"));

        Writer writer = optionalWriter.get();
        writer.writerReject(writerRejectCommand);
        return writerRepository.save(writer);
    }

    @PutMapping("/{id}/pubapprove")
    public Writer pubApprove(
        @PathVariable Long id,
        @RequestBody PubApproveCommand pubApproveCommand
    ) throws Exception {
        Optional<Writer> optionalWriter = writerRepository.findById(id);
        optionalWriter.orElseThrow(() -> new Exception("No Entity Found"));

        Writer writer = optionalWriter.get();
        writer.pubApprove(pubApproveCommand);
        return writerRepository.save(writer);
    }

    @PutMapping("/{id}/pubreject")
    public Writer pubReject(
        @PathVariable Long id,
        @RequestBody PubRejectCommand pubRejectCommand
    ) throws Exception {
        Optional<Writer> optionalWriter = writerRepository.findById(id);
        optionalWriter.orElseThrow(() -> new Exception("No Entity Found"));

        Writer writer = optionalWriter.get();
        writer.pubReject(pubRejectCommand);
        return writerRepository.save(writer);
    }
}
