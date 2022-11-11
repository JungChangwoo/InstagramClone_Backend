package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponse;
import instagram_clone.instagram_clone.config.BaseResponseStatus;
import instagram_clone.instagram_clone.controller.dto.post.*;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.service.PostService;
import instagram_clone.instagram_clone.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;

    @GetMapping("/posts/{userId}/main-feed")
    public BaseResponse<HomeFeedResponse> getHomeFeed(@PathVariable("userId") Long userId) {
        try {
            validateJWT(userId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        List<Post> posts = postService.findHomeFeeds(userId);
        HomeFeedResponse homeFeedResponse = new HomeFeedResponse(posts);

        return new BaseResponse<>(homeFeedResponse);
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

    private void validateJWT(Long userId) throws BaseException {
        Long userIdByJwt = jwtService.getUserIdx();
        if (userId != userIdByJwt){
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
    }

}
