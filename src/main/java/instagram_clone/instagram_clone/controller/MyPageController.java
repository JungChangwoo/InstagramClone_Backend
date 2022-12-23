package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponse;
import instagram_clone.instagram_clone.controller.dto.mypage.*;
import instagram_clone.instagram_clone.domain.Follow;
import instagram_clone.instagram_clone.domain.User;
//import instagram_clone.instagram_clone.service.FollowService;
import instagram_clone.instagram_clone.service.FollowService;
import instagram_clone.instagram_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        User user = userService.findByNicknameWithPost(nickname);
        return new BaseResponse<>(new GetMyPageResponse(user, userId));
    }

    @PostMapping("/follows")
    private BaseResponse<PostFollowResponse> follow(
            @RequestBody PostFollowRequest request){
        try {
            Long id = followService.follow(request.getUserId(), request.getFollowingNickname());
            return new BaseResponse<>(new PostFollowResponse(id));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/follows/status")
    private BaseResponse<PostFollowResponse> unFollow(@RequestBody PostFollowRequest request){
        try {
            Long id = followService.unFollow(request.getUserId(), request.getFollowingNickname());
            return new BaseResponse<>(new PostFollowResponse(id));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/follows/{userId}/followings")
    private BaseResponse<GetFollowingResponse> getFollowings(@PathVariable("userId") Long userId){
        List<Follow> follows = followService.findFollowings(userId);
        return new BaseResponse<>(new GetFollowingResponse(follows));
    }

    @GetMapping("/follows/{userId}/followers")
    private BaseResponse<GetFollowerResponse> getFollowers(@PathVariable("userId") Long userId){
        List<Follow> follows = followService.findFollowers(userId);
        return new BaseResponse<>(new GetFollowerResponse(follows));
    }

}
