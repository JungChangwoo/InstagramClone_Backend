package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @PersistenceContext
    private EntityManager em;

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public List<User> searchAll(String nickname) {
        return em.createQuery("select u from User u where u.nickname like :nickname", User.class)
                .setParameter("nickname", "%"+nickname+"%")
                .getResultList();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findByNicknameWithPost(String nickname) {
        return em.createQuery("select distinct u from User u" +
                                " join fetch u.posts p" +
                                " where u.nickname = : nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public void save(User user) {
        em.persist(user);
    }

    public List<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email = : email", User.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<User> findByPhone(String phoneNum) {
        return em.createQuery("select u from User u where u.phone = : phone", User.class)
                .setParameter("phone", phoneNum)
                .getResultList();
    }

    @Override
    public List<User> findByNickname(String nickname) {
        return em.createQuery("select u from User u" +
                        " where u.nickname = : nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
