package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponse;
import instagram_clone.instagram_clone.config.BaseResponseStatus;
import instagram_clone.instagram_clone.controller.dto.user.UpdateUserNicknameResponse;
import instagram_clone.instagram_clone.controller.dto.user.*;
import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.service.UserService;
import instagram_clone.instagram_clone.service.UserServiceImpl;
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

    @PostMapping("/users/join")
    public BaseResponse<CreateUserResponse> saveUser(@RequestBody PostUserRequest request) {
        try {
            Long id = userService.join(request);
            return new BaseResponse<>(new CreateUserResponse(id));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/users/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse loginResponse = userService.login(request);
            return new BaseResponse<>(loginResponse);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/users/phone")
    public BaseResponse validateDuplicatedPhone(@RequestParam("phone") String phone){
        try{
            userService.validateDuplicatedPhone(phone);
            return new BaseResponse(BaseResponseStatus.DUPLICATE_PHONE_SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse(e.getStatus());
        }
    }

    @GetMapping("/users/email")
    public BaseResponse validateDuplicatedEmail(@RequestParam("email") String email){
        try{
            userService.validateDuplicatedEmail(email);
            return new BaseResponse(BaseResponseStatus.DUPLICATE_EMAIL_SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse(e.getStatus());
        }
    }

    @PatchMapping("/users/{userId}/nickname")
    public BaseResponse<UpdateUserNicknameResponse> updateNickname(
            @PathVariable("userId") Long userId,
            @RequestBody String nickname) throws BaseException {
        try {
            validateJWT(userId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
        Long id = userService.updateNickname(userId, nickname);
        return new BaseResponse<>(new UpdateUserNicknameResponse(id));
    }

    @GetMapping("/users")
    public List<GetUserResponse> getUsers(){
        List<User> findUsers = userService.findUsers();
        List<GetUserResponse> collect = findUsers.stream()
                .map(m -> new GetUserResponse(m.getId(), m.getNickname(), m.getProfileImgUrl()))
                .collect(Collectors.toList());
        return collect;
    }

    private void validateJWT(Long userId) throws BaseException {
        Long userIdByJwt = jwtService.getUserIdx();
        if (userId != userIdByJwt){
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
    }

}
