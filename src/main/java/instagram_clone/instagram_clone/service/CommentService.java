package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.domain.Post;

import java.util.Optional;

public interface CommentService {
    public Long writeComment(Long userId, Long postId, String content);
    Post findOneWithComment(Long postId);
}
