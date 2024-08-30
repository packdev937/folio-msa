package kr.folio.user.application.mapper;

import kr.folio.user.domain.core.entity.User;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import kr.folio.user.presentation.dto.response.FeedsResponse;
import kr.folio.user.presentation.dto.response.RetrieveUserHomeResponse;
import kr.folio.user.presentation.dto.response.RetrieveUserHomeResponse.UserProfile;
import kr.folio.user.presentation.dto.response.UpdateUserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserDataMapper {

    public User toDomain(CreateUserRequest createUserRequest) {
        return User.builder()
            .id(createUserRequest.id())
            .nickname(createUserRequest.nickname())
            .birthday(createUserRequest.birthday())
            .build();
    }

    public CreateUserResponse toCreateResponse(User user, String message) {
        return new CreateUserResponse(user.getId(), message);
    }

    public UpdateUserResponse toUpdateResponse(User user, String message) {
        return new UpdateUserResponse(user.getId(), message);
    }

    public RetrieveUserHomeResponse toRetrieveUserHomeResponse(User user, FeedsResponse feeds) {
        return RetrieveUserHomeResponse.builder()
            .userProfile(new UserProfile(user))
            .feeds(feeds)
            .build();
    }
}
