package instagram_clone.instagram_clone.controller.dto.mypage;

import lombok.Data;

@Data
public class PostFollowRequest{
    private Long userId;
    private String followingNickname;
}