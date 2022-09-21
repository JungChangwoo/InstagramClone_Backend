package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Post> findAllById(Long id) {
        return em.createQuery("select p from Post p where p.user.id = : id", Post.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void save(Post post) {
        em.persist(post);
    }

    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }
}
