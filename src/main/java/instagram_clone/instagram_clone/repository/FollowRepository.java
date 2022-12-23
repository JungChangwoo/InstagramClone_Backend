package instagram_clone.instagram_clone.repository;

import instagram_clone.instagram_clone.domain.Follow;

import java.util.List;

public interface FollowRepository {
    public void save(Follow follow);
    public List<Follow> findByUserId(Long fromUserId, Long toUserId);
    public Follow findById(Long id);
    public List<Follow> findByFromUserId(Long userId);
    public List<Follow> findByFromUserIdWithToUser(Long userId);
    List<Follow> findByToUserIdWithFromUser(Long userId);
}
