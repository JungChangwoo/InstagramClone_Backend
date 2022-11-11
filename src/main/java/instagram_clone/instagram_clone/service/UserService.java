package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponse;
import instagram_clone.instagram_clone.config.BaseResponseStatus;
import instagram_clone.instagram_clone.controller.dto.user.LoginRequest;
import instagram_clone.instagram_clone.controller.dto.user.LoginResponse;
import instagram_clone.instagram_clone.controller.dto.user.CreateUserRequest;
import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.repository.UserRepository;
import instagram_clone.instagram_clone.utils.JwtService;
import instagram_clone.instagram_clone.utils.SHA256;
import instagram_clone.instagram_clone.utils.StringValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static instagram_clone.instagram_clone.config.BaseResponseStatus.*;
import static instagram_clone.instagram_clone.utils.StringValidation.isEmptyOrNull;

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
    public Long join(CreateUserRequest request) throws BaseException {
        validateDuplicatedNickname(request.getNickname());
        validateDuplicatedEmail(request.getEmail());

        User user = User.createUser(request);

        String pwd;
        try {
            pwd = new SHA256().encrypt(user.getPassword());
            user.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        userRepository.save(user);
        return user.getId();
    }

    public LoginResponse login(LoginRequest request) throws BaseException {
        validateLogin(request);

        User user = findByNickname(request.getNickname());

        String encryptPwd;
        try {
            encryptPwd = new SHA256().encrypt(request.getPassword());
        } catch (Exception exception){
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        if (user.getPassword().equals(encryptPwd)){
            Long id = user.getId();
            String jwt = jwtService.createJwt(id);
            return new LoginResponse(id, jwt);
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    @Transactional
    public void update(Long id, String name) {
        User user = userRepository.findById(id);
        user.setName(name);
    }

    public User findOne(Long id) {
        return userRepository.findById(id);
    }

    public User findByNickname(String nickname) throws BaseException {
        List<User> findUsers = userRepository.findByNickname(nickname);
        if (findUsers.isEmpty()){
            throw new BaseException(FAILED_TO_LOGIN);
        }
        return findUsers.get(0);
    }

    @Transactional
    public Long updateNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId);
        user.setNickname(nickname);
        return user.getId();
    }

    public void validateDuplicatedPhone(String phone) throws BaseException {
        List<User> findUsers = userRepository.findByPhone(phone);
        if (!findUsers.isEmpty()){
            throw new BaseException(DUPLICATED_PHONE);
        }
    }

    public void validateDuplicatedEmail(String email) throws BaseException {
        List<User> findUsers = userRepository.findByEmail(email);
        if (!findUsers.isEmpty()){
            throw new BaseException(DUPLICATED_EMAIL);
        }
    }
    private void validateDuplicatedNickname(String nickname) throws BaseException {
        List<User> findUsers = userRepository.findByNickname(nickname);
        if (!findUsers.isEmpty()){
            throw new BaseException(DUPLICATED_NICKNAME);
        }
    }

    private void validateLogin(LoginRequest request) throws BaseException {
        // 공백 검증
        if (isEmptyOrNull(request.getNickname()) || isEmptyOrNull(request.getPassword())){
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

}
