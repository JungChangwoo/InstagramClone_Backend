package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.controller.dto.HomeFeedRequest;
import instagram_clone.instagram_clone.domain.Comment;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.domain.PostImgUrl;
import instagram_clone.instagram_clone.service.PostService;
import instagram_clone.instagram_clone.utils.JwtService;
import lombok.AllArgsConstructor;
import lombok.Data;
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
        //Long idByJwt = jwtService.getUserIdx();
        //==팔로워들의 피드를 가져와야 함==//
        List<Post> posts = postService.findHomeFeeds(id);
        List<HomeFeedResponse> collect = posts.stream()
                .map(o -> new HomeFeedResponse(o))
                .collect(Collectors.toList());
        return collect;
    }

    @PostMapping("/posts/{id}")
    public PostPostResponse postUpload(@PathVariable("id") Long userId, @RequestBody PostPostRequest request) {
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

    @Data
    static class PatchPostRequest{
        private Long postId;
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class PatchPostResponse{
        private Long postId;
    }


    @Data
    static class PostPostRequest {
        private List<String> postImages;
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class PostPostResponse {
        private Long postId;
    }

    @Data
    static class HomeFeedResponse{
        private PostInfo postInfo;
        private List<PostImgUrlDto> postImages;

        public HomeFeedResponse(Post post){
            postInfo = new PostInfo(post);
            postImages = post.getPostImgUrls().stream()
                    .map(postImgUrl -> new PostImgUrlDto(postImgUrl))
                    .collect(Collectors.toList());
        }
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
    static class PostImgUrlDto{
        private String url;

        public PostImgUrlDto(PostImgUrl postImgUrl){
            url = postImgUrl.getUrl();
        }
    }

}
