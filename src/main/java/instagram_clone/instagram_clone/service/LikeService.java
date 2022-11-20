package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.domain.Like;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.repository.LikeRepository;
import instagram_clone.instagram_clone.repository.PostRepository;
import instagram_clone.instagram_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public Like like(Long userId, Long postId) {
        User user = userRepository.findById(userId);
        Post post = postRepository.findById(postId);
        Like like = Like.createLike(user, post);

        likeRepository.save(like);
        return like;
    }
}
