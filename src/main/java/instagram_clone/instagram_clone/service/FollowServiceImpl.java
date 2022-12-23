package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.config.BaseException;
import instagram_clone.instagram_clone.config.BaseResponseStatus;
import instagram_clone.instagram_clone.domain.Follow;
import instagram_clone.instagram_clone.domain.FollowStatus;
import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.repository.FollowRepository;
import instagram_clone.instagram_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static instagram_clone.instagram_clone.config.BaseResponseStatus.USERS_EMPTY_USER_NICKNAME;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long follow(Long fromUserId, String toUserNickname) throws BaseException {
        List<User> toUsers = userRepository.findByNickname(toUserNickname);
        if (toUsers.isEmpty()){
            throw new BaseException(USERS_EMPTY_USER_NICKNAME);
        }
        User toUser = toUsers.get(0);
        User fromUser = userRepository.findById(fromUserId);
        Follow follow = Follow.createFollow(fromUser, toUser);

        followRepository.save(follow);
        return follow.getId();
    }


    @Transactional
    public Long unFollow(Long fromUserId, String toUserNickname) throws BaseException {
        List<User> toUsers = userRepository.findByNickname(toUserNickname);
        if (toUsers.isEmpty()){
            throw new BaseException(USERS_EMPTY_USER_NICKNAME);
        }
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

    @Override
    public List<Follow> findFollowings(Long userId) {
        return followRepository.findByFromUserIdWithToUser(userId);
    }

    @Override
    public List<Follow> findFollowers(Long userId) {
        return followRepository.findByToUserIdWithFromUser(userId);
    }
}
