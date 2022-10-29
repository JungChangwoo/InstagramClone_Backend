package instagram_clone.instagram_clone.controller.dto.post;

import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.domain.PostImgUrl;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class HomeFeedResponse{
    private PostInfo postInfo;
    private List<PostImgUrlDto> postImages;

    public HomeFeedResponse(Post post){
        postInfo = new PostInfo(post);
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
        private String isLike;

        public PostInfo(Post post){
            postId = post.getId();
            nickname = post.getUser().getNickname();
            profileImgUrl = post.getUser().getProfileImgUrl();
            content = post.getContent();
            numOfComment = post.getComments().size();
        }
    }

    @Data
    static class PostImgUrlDto {
        private String url;

        public PostImgUrlDto(PostImgUrl postImgUrl) {
            url = postImgUrl.getUrl();
        }
    }
}
