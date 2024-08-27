package kr.folio.user.application.mapper;

import kr.folio.user.domain.core.entity.User;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
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

    public CreateUserResponse toResponse(User user, String message) {
        return new CreateUserResponse(user.getId(), message);
    }
}
