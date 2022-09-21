package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.controller.PostController;
import instagram_clone.instagram_clone.domain.Post;
import instagram_clone.instagram_clone.domain.PostImgUrl;
import instagram_clone.instagram_clone.domain.User;
import instagram_clone.instagram_clone.repository.PostRepository;
import instagram_clone.instagram_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> findHomeFeeds(Long userId) {
        return postRepository.findAllById(userId);
    }

    @Transactional
    public Long postUpload(Long userId, List<String> postImages, String content) {
        User user = userRepository.findById(userId);

        List<PostImgUrl> postImgUrls = new ArrayList<>();
        for (String postImgUrlString : postImages) {
            PostImgUrl postImgUrl = PostImgUrl.createPostImgUrl(postImgUrlString);
            postImgUrls.add(postImgUrl);
        }

        Post post = Post.createPost(user, content, postImgUrls);

        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public Long update(Long postId, String content) {
        Post post = postRepository.findById(postId);
        post.setContent(content);
        return post.getId();
    }

    public Post findOne(Long postId) {
        return postRepository.findById(postId);
    }
}
