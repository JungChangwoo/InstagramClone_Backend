package instagram_clone.instagram_clone.controller.dto.mypage;

import instagram_clone.instagram_clone.domain.Follow;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetFollowingResponse {
    List<FollowingUserInfo> followingUserInfoList;

    public GetFollowingResponse(List<Follow> findFollows){
        followingUserInfoList = new ArrayList<>();

        List<FollowingUserInfo> followingUserInfos = findFollows.stream()
                .map(f -> new FollowingUserInfo(f))
                .collect(Collectors.toList());

        for (FollowingUserInfo followingUserInfo : followingUserInfos){
            this.followingUserInfoList.add(followingUserInfo);
        }
    }

}
