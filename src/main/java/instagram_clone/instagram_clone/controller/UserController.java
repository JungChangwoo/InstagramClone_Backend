package instagram_clone.instagram_clone.controller;

import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<UserDto> members(){
        List<User> findUsers = userService.findUsers();
        List<UserDto> collect = findUsers.stream()
                .map(m -> new UserDto(m.getId(), m.getNickname(), m.getProfileImgUrl()))
                .collect(Collectors.toList());
        return collect;
    }

    @PostMapping("/users")
    public CreateUserResponse saveUser(@RequestBody CreateUserRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setNickname(request.getNickname());
        user.setPassword(request.getPassword());
        Long id = userService.join(user);
        return new CreateUserResponse(id);
    }

    @PutMapping("/users/{id}")
    public UpdateUserResponse updateUser(
        @PathVariable("id") Long id,
        @RequestBody UpdateUserRequest request){
        userService.update(id, request.getName());
        User findUser = userService.findOne(id);
        return new UpdateUserResponse(findUser.getId(), findUser.getName());
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class UserDto{
        private Long id;
        private String nickname;
        private String profileImgUrl;
    }

    @Data
    static class CreateUserRequest{
        private String nickname;
        private String password;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateUserResponse{
        private Long id;
    }

    @Data
    static class UpdateUserRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateUserResponse{
        private Long id;
        private String name;
    }
}
