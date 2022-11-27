package instagram_clone.instagram_clone.service;

public interface CommentService {
    public Long writeComment(Long userId, Long postId, String content);
}
