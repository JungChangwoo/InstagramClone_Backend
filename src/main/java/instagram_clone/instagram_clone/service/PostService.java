package instagram_clone.instagram_clone.service;

import instagram_clone.instagram_clone.controller.dto.post.PostImgRequest;
import instagram_clone.instagram_clone.controller.dto.post.PostImgUrlDto;
import instagram_clone.instagram_clone.domain.*;
import instagram_clone.instagram_clone.repository.FollowRepository;
import instagram_clone.instagram_clone.repository.PostRepository;
import instagram_clone.instagram_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public List<Post> findHomeFeeds(Long userId) {
        // 팔로잉하는 유저의 게시물
        List<Follow> followees = followRepository.findByFromUserId(userId);
        List<Post> posts = new ArrayList<>();
        for (Follow follow : followees) {
            if (follow.getStatus().equals(FollowStatus.ACTIVE)){
                List<Post> followeePosts = follow.getToUser().getPosts();
                for (Post post : followeePosts) {
                    if (post.getStatus().equals(PostStatus.ACTIVE)) {
                        posts.add(post);
                    }
                }
            }
        }
        // 자신의 게시물
        User user = userRepository.findById(userId);
        List<Post> userPosts = user.getPosts();
        for (Post post : userPosts){
            if (post.getStatus().equals(PostStatus.ACTIVE)) {
                posts.add(post);
            }
        }
        // update 순서대로
        posts.sort((d1, d2) -> d1.getUpdatedAt().compareTo(d2.getUpdatedAt()));
        return posts;
    }

    @Transactional
    public Long postUpload(Long userId, List<PostImgRequest> postImgRequests, String content) {
        User user = userRepository.findById(userId);

        List<PostImgUrl> postImgUrls = new ArrayList<>();
        for (PostImgRequest postImgRequest : postImgRequests) {
            PostImgUrl postImgUrl = PostImgUrl.createPostImgUrl(postImgRequest.getUrl());
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

    @Transactional
    public Long deletePost(Long postId) {
        Post post = postRepository.findById(postId);
        post.cancel();
        return post.getId();
    }
}
