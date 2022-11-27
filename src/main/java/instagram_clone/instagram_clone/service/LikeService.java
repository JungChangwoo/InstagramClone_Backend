package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.domain.Like;

public interface LikeService {

    public Like like(Long userId, Long postId);

}
