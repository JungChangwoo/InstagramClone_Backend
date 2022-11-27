package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponse;
import instagram_clone.instagram_clone.controller.dto.mypage.GetMyPageResponse;
import instagram_clone.instagram_clone.controller.dto.mypage.PostFollowRequest;
import instagram_clone.instagram_clone.controller.dto.mypage.PostFollowResponse;
import instagram_clone.instagram_clone.domain.User;
//import instagram_clone.instagram_clone.service.FollowService;
import instagram_clone.instagram_clone.service.FollowService;
import instagram_clone.instagram_clone.service.FollowServiceImpl;
import instagram_clone.instagram_clone.service.UserService;
import instagram_clone.instagram_clone.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/follows")
    private BaseResponse<PostFollowResponse> follow(
            @RequestBody PostFollowRequest request){
        Long id = followService.follow(request.getUserId(), request.getFollowingNickname());
        return new BaseResponse<>(new PostFollowResponse(id));
    }

    @PatchMapping("/follows/status")
    private BaseResponse<PostFollowResponse> unFollow(
            @RequestBody PostFollowRequest request){
        Long id = followService.unFollow(request.getUserId(), request.getFollowingNickname());
        return new BaseResponse<>(new PostFollowResponse(id));
    }

}
