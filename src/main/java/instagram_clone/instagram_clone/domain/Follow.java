package instagram_clone.instagram_clone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "follow")
@Getter @Setter
public class Follow {

    @Id @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @Enumerated(EnumType.STRING)
    private FollowStatus status;

    public Follow(){
    }

    public static Follow createFollow(User fromUser, User toUser) {
        Follow follow = new Follow();
        follow.setFromUser(fromUser);
        follow.setToUser(toUser);
        follow.setStatus(FollowStatus.ACTIVE);
        return follow;
    }

    public void cancel() {
        this.setStatus(FollowStatus.INACTIVE);
    }
}
