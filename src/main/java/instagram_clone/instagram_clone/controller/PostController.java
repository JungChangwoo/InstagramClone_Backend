package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponseStatus;
import instagram_clone.instagram_clone.controller.dto.post.HomeFeedRequest;
import instagram_clone.instagram_clone.controller.dto.post.*;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.service.PostService;
import instagram_clone.instagram_clone.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;

    @GetMapping("/posts/{id}/main-feed")
    public List<HomeFeedResponse> getHomeFeed(@PathVariable("id") Long id, @RequestBody HomeFeedRequest request) throws BaseException {
        //==팔로워들의 피드를 가져와야 함==//
        List<Post> posts = postService.findHomeFeeds(id);
        List<HomeFeedResponse> collect = posts.stream()
                .map(o -> new HomeFeedResponse(o))
                .collect(Collectors.toList());
        return collect;
    }

    @PostMapping("/posts/{id}")
    public PostPostResponse postUpload(@PathVariable("id") Long userId, @RequestBody PostPostRequest request) throws BaseException {
        Long userIdByJwt = jwtService.getUserIdx(); // Header에서 JWT 추출
        if (userId != userIdByJwt){
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
        Long postId = postService.postUpload(userId, request.getPostImages(), request.getContent());
        PostPostResponse postResponse = new PostPostResponse(postId);
        return postResponse;
    }

    @PatchMapping("/posts/{id}")
    public PatchPostResponse postUpdate(@PathVariable("id") Long id, @RequestBody PatchPostRequest request) {
        //==해당 User가 작성한 Post가 맞는지==//
        Long postId = postService.update(request.getPostId(), request.getContent());
        PatchPostResponse patchPostResponse = new PatchPostResponse(postId);
        return patchPostResponse;
    }

}
