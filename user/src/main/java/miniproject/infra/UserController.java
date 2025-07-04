package miniproject.infra;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import miniproject.domain.WriterRequestCommand;

import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // 상위 path 지정
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                            .collect(Collectors.toList());
    }

    // ✅ 회원가입은 POST로 처리
    @PostMapping
    public User register(
        @RequestBody RegisterCommand command,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        System.out.println("##### /users POST register called #####");
        User user = new User();
        user.register(command);

        return userRepository.save(user);
    }

    @PutMapping("/{id}/subscribe")
    public User subscribe(
        @PathVariable Long id,
        @RequestBody SubscribeCommand command
    ) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("No User Found"));
        user.subscribe(command);
        return userRepository.save(user);
    }

    @PutMapping("/{id}/writerquest")
    public User writerQuest(
        @PathVariable Long id,
        @RequestBody WriterQuestCommand command
    ) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("No User Found"));
        user.writerQuest(command);
        return userRepository.save(user);
    }

    @PutMapping("/{id}/cancelsubscription")
    public User cancelSubscription(
        @PathVariable Long id,
        @RequestBody CancelSubscriptionCommand command
    ) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("No User Found"));
        user.cancelSubscription(command);
        return userRepository.save(user);
    }

    @PutMapping("/{id}/chargepoint")
    public User chargePoint(
        @PathVariable Long id,
        @RequestBody ChargePointCommand command
    ) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("No User Found"));
        user.chargePoint(command);
        return userRepository.save(user);
    }
}
