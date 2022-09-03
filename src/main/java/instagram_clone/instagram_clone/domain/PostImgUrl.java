package instagram_clone.instagram_clone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostImgUrl {

    @Id @GeneratedValue
    @Column(name = "postimgurl_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String url;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
