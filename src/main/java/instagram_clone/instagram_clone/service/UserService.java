package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponseStatus;
import instagram_clone.instagram_clone.controller.dto.LoginRequest;
import instagram_clone.instagram_clone.controller.dto.LoginResponse;
import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.repository.UserRepository;
import instagram_clone.instagram_clone.utils.JwtService;
import instagram_clone.instagram_clone.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Long join(User user) throws BaseException {
        validateDuplicateUserNickname(user);
        validateDuplicateUserEmail(user);
        String pwd;
        try {
            pwd = new SHA256().encrypt(user.getPassword());
            user.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR);
        }
        userRepository.save(user);
        // JWT 발급
        Long userId = user.getId();
        System.out.println("userId: " + userId);
        jwtService.createJwt(userId);
        return user.getId();
    }

    private void validateDuplicateUserEmail(User user){
        List<User> findUsers = userRepository.findByEmail(user.getEmail());
        if (!findUsers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }
    private void validateDuplicateUserNickname(User user) {

    }

    @Transactional
    public void update(Long id, String name) {
        User user = userRepository.findById(id);
        user.setName(name);
    }

    public User findOne(Long id) {
        return userRepository.findById(id);
    }

    public User findByNickname(String nickname){
        List<User> user = userRepository.findByNickname(nickname);
        return user.get(0);
    }

    public LoginResponse login(LoginRequest request) throws BaseException {
        User user = findByNickname(request.getNickname());
        String encryptPwd;

        try {
            encryptPwd = new SHA256().encrypt(request.getPassword());
        } catch (Exception exception){
            throw new BaseException(BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR);
        }
        if (user.getPassword().equals(encryptPwd)){
            Long id = user.getId();
            String jwt = jwtService.createJwt(id);
            return new LoginResponse(id, jwt);
        } else {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }
}
