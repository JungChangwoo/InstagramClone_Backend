package instagram_clone.instagram_clone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse{
        private Long id;
        private String jwt;
}