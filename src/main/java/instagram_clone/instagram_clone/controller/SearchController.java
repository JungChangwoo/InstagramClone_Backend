package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseResponse;
import instagram_clone.instagram_clone.controller.dto.search.SearchUserInfo;
import instagram_clone.instagram_clone.controller.dto.search.SearchUserResponse;
import instagram_clone.instagram_clone.controller.dto.user.GetUserResponse;
import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.service.UserService;
import instagram_clone.instagram_clone.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/users/info")
    public BaseResponse<List<SearchUserInfo>> searchUser(@RequestParam("nickname") String nickname){
        List<User> findUsers = userService.searchUsers(nickname);
        System.out.println(findUsers);
        List<SearchUserInfo> response = findUsers.stream()
                .map(u -> new SearchUserInfo(u))
                .collect(Collectors.toList());
        return new BaseResponse<>(response);
    }
}
