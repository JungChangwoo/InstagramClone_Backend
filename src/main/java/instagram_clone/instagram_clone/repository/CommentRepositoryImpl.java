package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentRepositoryImpl implements CommentRepository{

    @PersistenceContext
    private EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }
}
