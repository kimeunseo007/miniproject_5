package miniproject.domain;

import lombok.Data;

@Data
public class WriteCommand {

    private String title;
    private String content;
    private Long writerId;
    private String writerNickname;
}
