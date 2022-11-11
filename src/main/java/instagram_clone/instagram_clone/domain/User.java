package instagram_clone.instagram_clone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import instagram_clone.instagram_clone.controller.dto.user.CreateUserRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "toUser")
    private List<Follow> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser")
    private List<Follow> followingList = new ArrayList<>();

    public static User createUser(String nickname, String password, String name, String phone, String email, String birth){
        User user = new User();
        user.setNickname(nickname);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBirth(birth);
        user.setUserStatus(UserStatus.ACTIVE);
        return user;
    }
    public static User createUser(CreateUserRequest request) {
        User user = new User();
        user.setNickname(request.getNickname());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setBirth(request.getBirth());
        user.setUserStatus(UserStatus.ACTIVE);
        return user;
    }
}
