package instagram_clone.instagram_clone.controller.dto.mypage;

import instagram_clone.instagram_clone.domain.Follow;
import instagram_clone.instagram_clone.domain.User;
import lombok.Data;

@Data
public class FollowingUserInfo {
    Long userId;
    String nickname;
    String profileImgUrl;

    public FollowingUserInfo(Follow follow){
        this.userId = follow.getToUser().getId();
        this.nickname = follow.getToUser().getNickname();
        this.profileImgUrl = follow.getToUser().getProfileImgUrl();
    }
}
