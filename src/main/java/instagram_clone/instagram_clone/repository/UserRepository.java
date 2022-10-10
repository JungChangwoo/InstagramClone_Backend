package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findByNickname(String nickname) {
        return em.createQuery("select u from User u where u.nickname = : nickname", User.class)
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
}
