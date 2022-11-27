package instagram_clone.instagram_clone.controller.dto.user;

import lombok.Data;

@Data
public class PostUserRequest {
    private String nickname;
    private String password;
    private String name;
    private String birth;
    private String phone;
    private String email;
}
