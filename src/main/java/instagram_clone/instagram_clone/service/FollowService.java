package instagram_clone.instagram_clone.service;

public interface FollowService {
    public Long follow(Long fromUserId, String toUserNickname);
    public Long unFollow(Long fromUserId, String toUserNickname);
}
