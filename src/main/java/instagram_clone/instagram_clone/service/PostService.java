package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.controller.dto.post.PostImgRequest;
import instagram_clone.instagram_clone.domain.Post;

import java.util.List;

public interface PostService {
    public List<Post> findHomeFeeds(Long userId);
    public Long postUpload(Long userId, List<PostImgRequest> postImgRequests, String content);
    public Long update(Long postId, String content);
    public Post findOne(Long postId);
    public Long deletePost(Long postId);
}
