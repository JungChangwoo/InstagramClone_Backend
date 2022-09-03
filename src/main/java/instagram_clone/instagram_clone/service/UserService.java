package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findByNickname(user.getNickname());
        if (!findUsers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
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
}
