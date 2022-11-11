package instagram_clone.instagram_clone.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse{
        private Long userId;
        private String jwt;
}