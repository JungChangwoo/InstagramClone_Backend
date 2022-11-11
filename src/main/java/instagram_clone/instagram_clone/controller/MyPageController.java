package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.controller.dto.mypage.GetMyPageResponse;
import instagram_clone.instagram_clone.controller.dto.mypage.PostFollowRequest;
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
    private GetMyPageResponse getMyPage(
            @PathVariable("userId") Long userId,
            @RequestParam("nickname") String nickname) throws BaseException {
        User user = userService.findByNickname(nickname);
        return new GetMyPageResponse(user, userId);
    }

    @PostMapping("/users/{userId}/following")
    private Long follow(
            @PathVariable("userId") Long userId,
            @RequestBody PostFollowRequest request){
        Long id = followService.follow(userId, request.getNickname());
        return id;
    }

    @PatchMapping("/users/{userId}/following/status")
    private Long unFollow(
            @PathVariable("userId") Long userId,
            @RequestBody PostFollowRequest request){
        Long id = followService.unFollow(userId, request.getNickname());
        return id;
    }

}
