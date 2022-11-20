package instagram_clone.instagram_clone.controller.dto.post;

import instagram_clone.instagram_clone.domain.Like;
import instagram_clone.instagram_clone.domain.LikeStatus;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.domain.PostImgUrl;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ResultPost {
    private PostInfo postInfo;
    private List<PostImgUrlDto> postImages;

    public ResultPost(Post post, Long userId){
        postInfo = new PostInfo(post, userId);
        postImages = post.getPostImgUrls().stream()
                    .map(postImgUrl -> new PostImgUrlDto(postImgUrl))
                    .collect(Collectors.toList());
    }

    @Data
    static class PostInfo{
        private Long postId;
        private String nickname;
        private String profileImgUrl;
        private String content;
        private String where;
        private int numOfLike;
        private int numOfComment;
        private String isLiked;

        public PostInfo(Post post, Long userId){
            postId = post.getId();
            nickname = post.getUser().getNickname();
            profileImgUrl = post.getUser().getProfileImgUrl();
            content = post.getContent();
            numOfComment = post.getComments().size();
            numOfLike = post.getLikes().size();
            isLiked = "N";
            List<Like> likes = post.getLikes();
            for (Like like : likes){
                if (like.getStatus().equals(LikeStatus.ACTIVE)){
                    if (like.getUser().getId() == userId){
                        isLiked = "Y";
                    }
                }
            }

        }
    }

}
