package instagram_clone.instagram_clone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    public static Comment createComment(User user, Post post, String content) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(content);
        return comment;
    }
}
