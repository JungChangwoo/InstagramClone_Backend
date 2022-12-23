package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.User;

import java.util.List;

public interface UserRepository {
    public List<User> findAll();
    public List<User> searchAll(String nickname);
    public User findById(Long id);
    public List<User> findByNicknameWithPost(String nickname);
    public void save(User user);
    public List<User> findByEmail(String email);
    public List<User> findByPhone(String phoneNum);
    List<User> findByNickname(String nickname);
}
