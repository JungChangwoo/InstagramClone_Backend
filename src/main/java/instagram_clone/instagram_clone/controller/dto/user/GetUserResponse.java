package instagram_clone.instagram_clone.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserResponse {
    private Long id;
    private String nickname;
    private String profileImgUrl;
}
