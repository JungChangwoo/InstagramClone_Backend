package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.Like;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class LikeRepositoryImpl implements LikeRepository{

    @PersistenceContext
    private EntityManager em;


    public void save(Like like) {
        em.persist(like);
    }
}
