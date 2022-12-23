package instagram_clone.instagram_clone.controller.dto.mypage;

import instagram_clone.instagram_clone.domain.Follow;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetFollowerResponse {
    List<FollowerUserInfo> followerUserInfoList;

    public GetFollowerResponse(List<Follow> findFollows){
        followerUserInfoList = new ArrayList<>();

        List<FollowerUserInfo> followerUserInfos = findFollows.stream()
                .map(f -> new FollowerUserInfo(f))
                .collect(Collectors.toList());

        for (FollowerUserInfo followerUserInfo : followerUserInfos){
            this.followerUserInfoList.add(followerUserInfo);
        }
    }

}
