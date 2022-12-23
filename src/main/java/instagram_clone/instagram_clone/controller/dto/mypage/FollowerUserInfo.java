package instagram_clone.instagram_clone.controller.dto.mypage;

import instagram_clone.instagram_clone.domain.Follow;
import lombok.Data;

@Data
public class FollowerUserInfo {
    Long userId;
    String nickname;
    String profileImgUrl;

    public FollowerUserInfo(Follow follow){
        this.userId = follow.getFromUser().getId();
        this.nickname = follow.getFromUser().getNickname();
        this.profileImgUrl = follow.getFromUser().getProfileImgUrl();
    }
}
