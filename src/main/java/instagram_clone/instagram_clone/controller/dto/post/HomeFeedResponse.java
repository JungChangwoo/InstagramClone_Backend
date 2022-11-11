package instagram_clone.instagram_clone.controller.dto.post;

import instagram_clone.instagram_clone.domain.Post;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HomeFeedResponse {
    private List<ResultPost> post;

    public HomeFeedResponse(List<Post> posts){
        post = new ArrayList<>();
        // Post --> ResultPost
        List<ResultPost> resultPosts = posts.stream()
                .map(o -> new ResultPost(o))
                .collect(Collectors.toList());

        for (ResultPost resultPost : resultPosts){
            post.add(resultPost);
        }
    }
}
