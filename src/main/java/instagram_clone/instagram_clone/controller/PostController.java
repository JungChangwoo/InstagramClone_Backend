package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponse;
import instagram_clone.instagram_clone.config.BaseResponseStatus;
import instagram_clone.instagram_clone.controller.dto.DeletePostResponse;
import instagram_clone.instagram_clone.controller.dto.PostLikeResponse;
import instagram_clone.instagram_clone.controller.dto.post.*;
import instagram_clone.instagram_clone.domain.Like;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.service.LikeService;
import instagram_clone.instagram_clone.service.LikeServiceImpl;
import instagram_clone.instagram_clone.service.PostService;
import instagram_clone.instagram_clone.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;
    private final LikeService likeService;

    @GetMapping("/posts/{userId}/main-feed")
    public BaseResponse<HomeFeedResponse> getHomeFeed(@PathVariable("userId") Long userId) {
        try {
            validateJWT(userId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        List<Post> posts = postService.findHomeFeeds(userId);
        HomeFeedResponse homeFeedResponse = new HomeFeedResponse(posts, userId);

        return new BaseResponse<>(homeFeedResponse);
    }

    @PostMapping("/posts/{userId}/likes")
    public BaseResponse<PostLikeResponse> postLike(@PathVariable("userId") Long userId, @RequestBody Long postId){
        try {
            validateJWT(userId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
        Like like = likeService.like(userId, postId);
        return new BaseResponse<>(new PostLikeResponse(like.getStatus().name()));
    }

    @PostMapping("/posts/{userId}")
    public BaseResponse<PostPostResponse> postUpload(@PathVariable("userId") Long userId, @RequestBody PostPostRequest request) throws BaseException {
        try {
            validateJWT(userId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        Long postId = postService.postUpload(userId, request.getPostImages(), request.getContent());
        PostPostResponse postResponse = new PostPostResponse(postId);
        return new BaseResponse<>(postResponse);
    }

    @PatchMapping("/posts/{userId}")
    public BaseResponse<PatchPostResponse> postUpdate(@PathVariable("userId") Long userId, @RequestBody PatchPostRequest request) {
        try {
            validateJWT(userId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        Long postId = postService.update(request.getPostId(), request.getContent());
        PatchPostResponse patchPostResponse = new PatchPostResponse(postId);
        return new BaseResponse<>(patchPostResponse);
    }

    @PatchMapping("posts/{userId}/status")
    public BaseResponse<DeletePostResponse> deletePost(@PathVariable("userId") Long userId, @RequestBody Long postId){
        try {
            validateJWT(userId);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        Long id = postService.deletePost(postId);
        return new BaseResponse<>(new DeletePostResponse(id));
    }

    private void validateJWT(Long userId) throws BaseException {
        Long userIdByJwt = jwtService.getUserIdx();
        if (userId != userIdByJwt){
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
    }

}
