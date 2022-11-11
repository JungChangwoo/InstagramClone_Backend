package instagram_clone.instagram_clone.controller.dto.post;

import lombok.Data;

import java.util.List;

@Data
public class PostPostRequest {
    private List<PostImgRequest> postImages;
    private String content;
}