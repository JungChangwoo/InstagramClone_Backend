package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponse;
import instagram_clone.instagram_clone.config.BaseResponseStatus;
import instagram_clone.instagram_clone.controller.dto.comment.GetCommentResponse;
import instagram_clone.instagram_clone.controller.dto.comment.PostCommentRequest;
import instagram_clone.instagram_clone.controller.dto.comment.PostCommentResponse;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.service.CommentService;
import instagram_clone.instagram_clone.service.CommentServiceImpl;
import instagram_clone.instagram_clone.service.PostService;
import instagram_clone.instagram_clone.service.PostServiceImpl;
import instagram_clone.instagram_clone.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final JwtService jwtService;

    @GetMapping("/posts/{userId}/{postId}/comments")
    public BaseResponse<GetCommentResponse> getComments(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId){
        try {
            validateJWT(userId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        Post post = postService.findOne(postId);
        GetCommentResponse response = new GetCommentResponse(post);
        return new BaseResponse<>(response);
    }

    @PostMapping("/posts/{userId}/comments")
    public BaseResponse<PostCommentResponse> postComment(@PathVariable("userId") Long userId, @RequestBody PostCommentRequest request) {
        try {
            validateJWT(userId);
        } catch (BaseException e) {
            e.printStackTrace();
        }

        Long commentId = commentService.writeComment(userId, request.getPostId(), request.getContent());
        PostCommentResponse postCommentResponse = new PostCommentResponse(commentId);
        return new BaseResponse<>(postCommentResponse);
    }

    private void validateJWT(Long userId) throws BaseException {
        Long userIdByJwt = jwtService.getUserIdx();
        if (userId != userIdByJwt){
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
    }

}
