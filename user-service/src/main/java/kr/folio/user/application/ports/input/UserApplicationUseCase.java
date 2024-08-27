package kr.folio.user.application.ports.input;

import jakarta.validation.Valid;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import kr.folio.user.presentation.dto.response.ValidateUserResponse;

public interface UserApplicationUseCase {
    CreateUserResponse createUser(@Valid CreateUserRequest createUserRequest);

    ValidateUserResponse validateUserId(String id);

    ValidateUserResponse validateUserNickname(String nickname);
}
