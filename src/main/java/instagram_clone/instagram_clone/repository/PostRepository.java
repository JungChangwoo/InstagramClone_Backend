package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.Post;

import java.util.List;

public interface PostRepository {
    public void save(Post post);
    public List<Post> findAllByUserId(Long id);
    public Post findById(Long postId);
    public void delete(Post post);
}
