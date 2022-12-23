package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Post> findAllByUserId(Long id) {
        return em.createQuery("select p from Post p" +
                                "join fetch p.user u" +
                                "where p.user.id = : id", Post.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Post> findAllByUserIdWithPIU(Long id) {
        return em.createQuery("select distinct p from Post p" +
                                " join fetch p.user u" +
                                " join fetch p.postImgUrls piu" +
                                " where p.user.id = : id", Post.class)
                .setParameter("id", id)
                .getResultList();
    }



    @Override
    public void save(Post post) {
        em.persist(post);
    }

    @Override
    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }

    @Override
    public void delete(Post post) {
        em.remove(post);
    }

    @Override
    public Optional<Post> findByIdWithComment(Long postId) {
        return em.createQuery("select distinct p from Post p" +
                                " join fetch p.user u" +
                                " join fetch p.comments c" +
                                " join fetch c.user cu" +
                                " where p.id = : id", Post.class)
                .setParameter("id", postId)
                .getResultList()
                .stream().findAny();
    }
}
