package instagram_clone.instagram_clone.controller.dto.search;

import instagram_clone.instagram_clone.domain.User;
import lombok.Data;

@Data
public class SearchUserInfo {
    private String profileImgUrl;
    private String nickname;
    private String name;

    public SearchUserInfo(User user){
        this.profileImgUrl = user.getProfileImgUrl();
        this.nickname = user.getNickname();
        this.name = user.getName();
    }
}
