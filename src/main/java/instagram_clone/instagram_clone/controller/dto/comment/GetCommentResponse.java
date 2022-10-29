package instagram_clone.instagram_clone.controller.dto.comment;

import instagram_clone.instagram_clone.domain.Comment;
import instagram_clone.instagram_clone.domain.Post;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetCommentResponse {
    private PostShortInfo postShortInfo;
    private List<CommentInfo> commentInfoList;

    public GetCommentResponse(Post post) {
        postShortInfo = new PostShortInfo(post);
        commentInfoList = post.getComments().stream()
                .map(comment -> new CommentInfo(comment))
                .collect(Collectors.toList());
    }

    @Data
    static class PostShortInfo {
        private String nickname;
        private String profileImgUrl;
        private String content;

        public PostShortInfo(Post post) {
            nickname = post.getUser().getNickname();
            profileImgUrl = post.getUser().getProfileImgUrl();
            content = post.getContent();
        }
    }

    @Data
    static class CommentInfo {
        private Long commentId;
        private String nickname;
        private String profileImgUrl;
        private String content;

        public CommentInfo(Comment comment) {
            commentId = comment.getId();
            nickname = comment.getUser().getNickname();
            profileImgUrl = comment.getUser().getProfileImgUrl();
            content = comment.getContent();
        }
    }
}