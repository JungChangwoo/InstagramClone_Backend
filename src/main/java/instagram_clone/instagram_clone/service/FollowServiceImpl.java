package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.domain.Follow;
import instagram_clone.instagram_clone.domain.FollowStatus;
import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.repository.FollowRepository;
import instagram_clone.instagram_clone.repository.FollowRepositoryImpl;
import instagram_clone.instagram_clone.repository.UserRepository;
import instagram_clone.instagram_clone.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long follow(Long fromUserId, String toUserNickname){
        List<User> toUsers = userRepository.findByNickname(toUserNickname);
        User toUser = toUsers.get(0);
        User fromUser = userRepository.findById(fromUserId);
        Follow follow = Follow.createFollow(fromUser, toUser);

        followRepository.save(follow);
        return follow.getId();
    }


    @Transactional
    public Long unFollow(Long fromUserId, String toUserNickname) {
        List<User> toUsers = userRepository.findByNickname(toUserNickname);
        User toUser = toUsers.get(0);
        User fromUser = userRepository.findById(fromUserId);

        List<Follow> follows = followRepository.findByUserId(fromUser.getId(), toUser.getId());

        for (Follow follow : follows){
            if (follow.getStatus().equals(FollowStatus.ACTIVE)){
                follow.cancel();
                return follow.getId();
            }
        }
        return follows.get(0).getId();
    }
}
