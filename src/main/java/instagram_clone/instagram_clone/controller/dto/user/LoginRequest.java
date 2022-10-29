package instagram_clone.instagram_clone.controller.dto.user;

import lombok.Data;

@Data
public class LoginRequest{
    private String nickname;
    private String password;
}