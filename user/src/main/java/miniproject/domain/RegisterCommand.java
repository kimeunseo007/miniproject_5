package miniproject.domain;

import lombok.Data;

@Data
public class RegisterCommand {
    private String email;
    private String nickname;
    private String passwordHash;
}