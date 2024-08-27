package kr.folio.user.application.ports.input;

import jakarta.validation.Valid;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;

public interface UserApplicationUseCase {
    CreateUserResponse createUser(@Valid CreateUserRequest createUserRequest);
}
