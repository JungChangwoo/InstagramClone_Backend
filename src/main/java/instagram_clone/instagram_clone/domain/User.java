package instagram_clone.instagram_clone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String password;

    private String name;

    private String profileImgUrl;

    private String phone;

    private String email;

    private String birth;

    private String website;

    private String introduce;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "toUser")
    private List<Follow> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser")
    private List<Follow> followingList = new ArrayList<>();

}
