package kr.folio.user.application.ports.input;

import jakarta.validation.Valid;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.request.UpdateBirthdayRequest;
import kr.folio.user.presentation.dto.request.UpdateMessageRequest;
import kr.folio.user.presentation.dto.request.UpdateNicknameRequest;
import kr.folio.user.presentation.dto.request.UpdateProfileImageUrlRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import kr.folio.user.presentation.dto.response.DeleteUserResponse;
import kr.folio.user.presentation.dto.response.UpdateUserResponse;
import kr.folio.user.presentation.dto.response.ValidateUserResponse;

public interface UserApplicationUseCase {

    CreateUserResponse createUser(@Valid CreateUserRequest createUserRequest);

    ValidateUserResponse validateUserId(String id);

    ValidateUserResponse validateUserNickname(String nickname);

    UpdateUserResponse updateUserNickname(String id, UpdateNicknameRequest request);

    UpdateUserResponse updateUserBirthday(String id, UpdateBirthdayRequest request);

    UpdateUserResponse updateUserMessage(String id, UpdateMessageRequest request);

    UpdateUserResponse updateUserProfileImage(String id, UpdateProfileImageUrlRequest request);

    DeleteUserResponse deleteUser(String id);
}
