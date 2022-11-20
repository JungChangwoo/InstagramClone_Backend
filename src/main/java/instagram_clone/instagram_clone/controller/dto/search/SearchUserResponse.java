package instagram_clone.instagram_clone.controller.dto.search;

import instagram_clone.instagram_clone.domain.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SearchUserResponse {
    private List<SearchUserInfo> searchUserInfoList;

    public SearchUserResponse(List<User> findUsers) {
        this.searchUserInfoList = new ArrayList<>();

        List<SearchUserInfo> searchUserInfos = findUsers.stream()
                .map(u -> new SearchUserInfo(u))
                .collect(Collectors.toList());

        for (SearchUserInfo searchUserInfo : searchUserInfos){
            this.searchUserInfoList.add(searchUserInfo);
        }

    }
}
