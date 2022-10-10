package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.Follow;
import instagram_clone.instagram_clone.domain.FollowStatus;
import instagram_clone.instagram_clone.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class FollowRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Follow follow){
        em.persist(follow);
    }

    public List<Follow> findByUserId(Long fromUserId, Long toUserId) {
        return em.createQuery("select f from Follow f " +
                "where f.fromUser.id = :fromUserId " +
                "and f.toUser.id = :toUserId", Follow.class)
                .setParameter("fromUserId", fromUserId)
                .setParameter("toUserId", toUserId)
                .getResultList();
    }

    public Follow findById(Long id) {
        return em.find(Follow.class, id);
    }

    public List<Follow> findByFromUserId(Long userId) {
        return em.createQuery("select f from Follow f " +
                        "where f.fromUser.id = : id " +
                        "and f.status = :status", Follow.class)
                .setParameter("id", userId)
                .setParameter("status", FollowStatus.ACTIVE)
                .getResultList();
    }
}