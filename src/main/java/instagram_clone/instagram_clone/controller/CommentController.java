package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.controller.dto.comment.GetCommentResponse;
import instagram_clone.instagram_clone.controller.dto.comment.PostCommentRequest;
import instagram_clone.instagram_clone.controller.dto.comment.PostCommentResponse;
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
    public PostCommentResponse postComment(@PathVariable("userId") Long userId, @RequestBody PostCommentRequest request) {
        Long commentId = commentService.writeComment(userId, request.getPostId(), request.getContent());
        PostCommentResponse postCommentResponse = new PostCommentResponse(commentId);
        return postCommentResponse;
    }

}
