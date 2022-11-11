package instagram_clone.instagram_clone.controller.dto.post;

import instagram_clone.instagram_clone.domain.PostImgUrl;
import lombok.Data;

@Data
public class PostImgUrlDto {
    private String url;

    public PostImgUrlDto(PostImgUrl postImgUrl) {
            url = postImgUrl.getUrl();
        }
}