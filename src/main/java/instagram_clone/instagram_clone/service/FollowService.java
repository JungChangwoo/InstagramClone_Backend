package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.domain.Follow;

import java.util.List;

public interface FollowService {
    public Long follow(Long fromUserId, String toUserNickname) throws BaseException;
    public Long unFollow(Long fromUserId, String toUserNickname) throws BaseException;
    List<Follow> findFollowings(Long userId);
    List<Follow> findFollowers(Long userId);
}
