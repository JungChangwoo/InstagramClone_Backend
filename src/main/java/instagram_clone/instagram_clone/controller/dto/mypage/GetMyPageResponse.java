package instagram_clone.instagram_clone.controller.dto.mypage;

import instagram_clone.instagram_clone.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetMyPageResponse {
    UserInfo userInfo;
    List<PostInfo> postInfo;

    public GetMyPageResponse(User user, Long userId) {
        userInfo = new UserInfo(user, userId);
        postInfo = new ArrayList<>();
        List<Post> posts = user.getPosts();
        for (Post post : posts) {
            if (post.getStatus().equals(PostStatus.ACTIVE)) {
                postInfo.add(new PostInfo(post.getPostImgUrls().get(0).getUrl()));
            }
        }
    }

    @Data
    @AllArgsConstructor
    static class PostInfo {
        private String imgUrl;
    }

    @Data
    static class UserInfo {
        private String nickname;
        private String profileImgUrl;
        private int numOfPost;
        private int follower;
        private int followee;
        private String name;
        private String isFollowed; // Y or N

        public UserInfo(User user, Long userId) {
            nickname = user.getNickname();
            profileImgUrl = user.getProfileImgUrl();
            numOfPost = user.getPosts().size();
            follower = this.get_follow_count(user.getFollowerList());
            followee = this.get_follow_count(user.getFollowingList());
            name = user.getName();
            List<Follow> follows = user.getFollowerList();
            isFollowed = "N";
            for (Follow follow : follows) {
                if (follow.getFromUser().getId().equals(userId)) {
                    isFollowed = "Y";
                }
            }

        }

        private int get_follow_count(List<Follow> follows){
            int count = 0;
            for (Follow follow : follows){
                if (follow.getStatus().equals(FollowStatus.ACTIVE)){
                    count += 1;
                }
            }
            return count;
        }
    }
}