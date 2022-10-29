package instagram_clone.instagram_clone.controller.dto.post;

import lombok.Data;

@Data
public class PatchPostRequest{
    private Long postId;
    private String content;
}