package instagram_clone.instagram_clone.controller.dto.comment;

import lombok.Data;

@Data
public class PostCommentRequest{
    private Long postId;
    private String content;
}