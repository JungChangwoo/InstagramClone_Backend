package instagram_clone.instagram_clone.controller.dto.mypage;

import instagram_clone.instagram_clone.domain.Follow;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.domain.User;
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
            postInfo.add(new PostInfo(post.getPostImgUrls().get(0).getUrl()));
        }
    }

    @Data
    static class PostInfo {
        private String imageUrl;

        public PostInfo(String imageUrl) {
            this.imageUrl = imageUrl;
        }
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
            follower = user.getFollowerList().size();
            followee = user.getFollowingList().size();
            name = user.getName();
            List<Follow> follows = user.getFollowerList();
            isFollowed = "N";
            for (Follow follow : follows) {
                if (follow.getFromUser().equals(userId)) {
                    isFollowed = "Y";
                }
            }

        }
    }
}