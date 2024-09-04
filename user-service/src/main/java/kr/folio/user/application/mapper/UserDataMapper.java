package kr.folio.user.application.mapper;

import kr.folio.user.domain.core.entity.User;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.user.CreateUserResponse;
import kr.folio.user.presentation.dto.response.feed.FeedsResponse;
import kr.folio.user.presentation.dto.response.user.UserHomeResponse;
import kr.folio.user.presentation.dto.response.user.UpdateUserResponse;
import kr.folio.user.presentation.dto.response.user.UserProfileResponse;
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

    public UserHomeResponse toRetrieveUserHomeResponse(UserProfileResponse userProfileResponse,
        FeedsResponse feeds) {
        return UserHomeResponse.builder()
            .userProfile(userProfileResponse)
            .feeds(feeds)
            .build();
    }

    public UserProfileResponse toUserProfileResponse(User user) {
        return UserProfileResponse.of(user);
    }
}
