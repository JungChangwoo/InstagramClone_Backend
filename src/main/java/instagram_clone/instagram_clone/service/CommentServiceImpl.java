package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.domain.Comment;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long writeComment(Long userId, Long postId, String content) {
        User user = userRepository.findById(userId);
        Post post = postRepository.findById(postId);

        Comment comment = Comment.createComment(user, post, content);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    public Post findOneWithComment(Long postId) {
        Optional<Post> postWithComment = postRepository.findByIdWithComment(postId);
        if (postWithComment.isPresent() == false){
            return postRepository.findById(postId);
        } else {
            return postWithComment.get();
        }
    }
}
