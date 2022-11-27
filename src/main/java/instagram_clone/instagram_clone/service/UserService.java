package instagram_clone.instagram_clone.service;


import com.fasterxml.jackson.databind.ser.Serializers;
import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.controller.dto.user.LoginRequest;
import instagram_clone.instagram_clone.controller.dto.user.LoginResponse;
import instagram_clone.instagram_clone.controller.dto.user.PostUserRequest;
import instagram_clone.instagram_clone.domain.User;

import java.util.List;

public interface UserService {
    public Long join(PostUserRequest request) throws BaseException;
    public LoginResponse login(LoginRequest request) throws BaseException;
    public User findByNickname(String nickname) throws BaseException;
    public void validateDuplicatedPhone(String phone) throws BaseException;
    public void validateDuplicatedEmail(String email) throws BaseException;
    public Long updateNickname(Long userId, String nickname) throws BaseException;
    public User findOne(Long userId);
    public List<User> findUsers();
    List<User> searchUsers(String nickname);
}
