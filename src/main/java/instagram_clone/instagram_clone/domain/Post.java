package instagram_clone.instagram_clone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImgUrl> postImgUrls = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    //==연관관계 메서드==//
    public void addPostImgUrl(PostImgUrl postImgUrl){
        postImgUrls.add(postImgUrl);
        postImgUrl.setPost(this);
    }

    public static Post createPost(User user, String content, List<PostImgUrl> postImgUrls){
        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        for (PostImgUrl postImgUrl : postImgUrls){
            post.addPostImgUrl(postImgUrl);
        }
        return post;
    }
}
