package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    public void save(Post post);
    public List<Post> findAllByUserId(Long id);
    public List<Post> findAllByUserIdWithPIU(Long id);
    public Post findById(Long postId);
    public void delete(Post post);
    Optional<Post> findByIdWithComment(Long postId);
}
