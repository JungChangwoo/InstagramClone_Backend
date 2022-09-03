package instagram_clone.instagram_clone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter @Setter
public class Follow {

    @Id @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User followee;

}
