package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponse;
import instagram_clone.instagram_clone.controller.dto.mypage.GetMyPageResponse;
import instagram_clone.instagram_clone.controller.dto.mypage.PostFollowRequest;
import instagram_clone.instagram_clone.controller.dto.mypage.PostFollowResponse;
import instagram_clone.instagram_clone.domain.Follow;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.domain.User;
//import instagram_clone.instagram_clone.service.FollowService;
import instagram_clone.instagram_clone.service.FollowService;
import instagram_clone.instagram_clone.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final FollowService followService;
    private final UserService userService;

    @GetMapping("/users/{userId}/my-page")
    private BaseResponse<GetMyPageResponse> getMyPage(
            @PathVariable("userId") Long userId,
            @RequestParam("nickname") String nickname) throws BaseException {
        User user = userService.findByNickname(nickname);
        return new BaseResponse<>(new GetMyPageResponse(user, userId));
    }

    @PostMapping("/users/{userId}/following")
    private BaseResponse<PostFollowResponse> follow(
            @PathVariable("userId") Long userId,
            @RequestBody PostFollowRequest request){
        Long id = followService.follow(userId, request.getFollowingNickname());
        return new BaseResponse<>(new PostFollowResponse(id));
    }

    @PatchMapping("/users/{userId}/following/status")
    private BaseResponse<PostFollowResponse> unFollow(
            @PathVariable("userId") Long userId,
            @RequestBody PostFollowRequest request){
        Long id = followService.unFollow(userId, request.getFollowingNickname());
        return new BaseResponse<>(new PostFollowResponse(id));
    }

}
