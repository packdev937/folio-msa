package kr.folio.user.application.mapper;

import kr.folio.user.domain.core.entity.User;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import kr.folio.user.presentation.dto.response.FeedsResponse;
import kr.folio.user.presentation.dto.response.RetrieveUserHomeResponse;
import kr.folio.user.presentation.dto.response.UpdateUserResponse;
import kr.folio.user.presentation.dto.response.UserProfileResponse;
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

    public RetrieveUserHomeResponse toRetrieveUserHomeResponse(UserProfileResponse userProfileResponse, FeedsResponse feeds) {
        return RetrieveUserHomeResponse.builder()
            .userProfile(userProfileResponse)
            .feeds(feeds)
            .build();
    }

    public UserProfileResponse toUserProfileResponse(User user) {
        return UserProfileResponse.of(user);
    }
}
