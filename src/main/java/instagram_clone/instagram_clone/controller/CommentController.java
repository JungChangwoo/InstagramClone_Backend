package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.domain.Comment;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.service.CommentService;
import instagram_clone.instagram_clone.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/posts/{userId}/{postId}/comments")
    public GetCommentResponse getComments(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId){
        //==유저Id 확인==//
        Post post = postService.findOne(postId);
        GetCommentResponse response = new GetCommentResponse(post);
        return response;
    }

    @PostMapping("/posts/{userId}/comments")
    public PostCommentResponse postComment(@PathVariable("userId") Long userId, @RequestBody PostCommentRequest request){
        Long commentId = commentService.writeComment(userId, request.getPostId(), request.getContent());
        PostCommentResponse postCommentResponse = new PostCommentResponse(commentId);
        return postCommentResponse;
    }

    @Data
    static class PostCommentRequest{
        private Long postId;
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class PostCommentResponse{
        private Long commentId;
    }

    @Data
    static class GetCommentResponse{
        private PostShortInfo postShortInfo;
        private List<CommentInfo> commentInfoList;

        public GetCommentResponse(Post post){
            postShortInfo = new PostShortInfo(post);
            commentInfoList = post.getComments().stream()
                    .map(comment -> new CommentInfo(comment))
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class PostShortInfo{
        private String nickname;
        private String profileImgUrl;
        private String content;

        public PostShortInfo(Post post){
            nickname = post.getUser().getNickname();
            profileImgUrl = post.getUser().getProfileImgUrl();
            content = post.getContent();
        }
    }

    @Data
    static class CommentInfo{
        private Long commentId;
        private String nickname;
        private String profileImgUrl;
        private String content;

        public CommentInfo(Comment comment){
            commentId = comment.getId();
            nickname = comment.getUser().getNickname();
            profileImgUrl = comment.getUser().getProfileImgUrl();
            content = comment.getContent();
        }
    }
}
