package instagram_clone.instagram_clone.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponse;
import instagram_clone.instagram_clone.controller.dto.user.UpdateUserNicknameRequest;
import instagram_clone.instagram_clone.controller.dto.user.UpdateUserNicknameResponse;
import instagram_clone.instagram_clone.controller.dto.user.*;
import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.service.UserService;
import instagram_clone.instagram_clone.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/users")
    public List<GetUserResponse> members(){
        List<User> findUsers = userService.findUsers();
        List<GetUserResponse> collect = findUsers.stream()
                .map(m -> new GetUserResponse(m.getId(), m.getNickname(), m.getProfileImgUrl()))
                .collect(Collectors.toList());
        return collect;
    }

    @PostMapping("/users/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws BaseException {
        // 닉네임 공백 처리
        // 비밀번호 공백 처리
        LoginResponse loginResponse= userService.login(request);
        return loginResponse;
    }

    @PostMapping("/users/join")
    public BaseResponse<CreateUserResponse> saveUser(@RequestBody CreateUserRequest request) throws BaseException {
        Long id = userService.join(request);
        return new BaseResponse<>(new CreateUserResponse(id));
    }

    @PatchMapping("/users/{userId}/nickname")
    public BaseResponse<UpdateUserNicknameResponse> updateNickname(
        @PathVariable("userId") Long userId,
        @RequestBody UpdateUserNicknameRequest request){
        Long id = userService.updateNickname(userId, request.getNickname());
        return new BaseResponse<>(new UpdateUserNicknameResponse(id));
    }

    @PutMapping("/users/{id}")
    public UpdateUserResponse updateUser(
        @PathVariable("id") Long id,
        @RequestBody UpdateUserRequest request){
        userService.update(id, request.getName());
        User findUser = userService.findOne(id);
        return new UpdateUserResponse(findUser.getId(), findUser.getName());
    }

}
