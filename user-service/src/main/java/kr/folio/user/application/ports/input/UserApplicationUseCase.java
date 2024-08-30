package kr.folio.user.application.ports.input;

import jakarta.validation.Valid;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.request.UpdateBirthdayRequest;
import kr.folio.user.presentation.dto.request.UpdateMessageRequest;
import kr.folio.user.presentation.dto.request.UpdateNicknameRequest;
import kr.folio.user.presentation.dto.request.UpdateProfileImageUrlRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import kr.folio.user.presentation.dto.response.DeleteUserResponse;
import kr.folio.user.presentation.dto.response.RetrieveUserHomeResponse;
import kr.folio.user.presentation.dto.response.UpdateUserResponse;
import kr.folio.user.presentation.dto.response.UserProfileResponse;
import kr.folio.user.presentation.dto.response.ValidateUserResponse;

public interface UserApplicationUseCase {

    CreateUserResponse createUser(@Valid CreateUserRequest createUserRequest);

    ValidateUserResponse validateUserId(String id);

    ValidateUserResponse validateUserNickname(String nickname);

    UpdateUserResponse updateUserNickname(String requestUserId,
        @Valid UpdateNicknameRequest updateNicknameRequest);

    UpdateUserResponse updateUserBirthday(String requestUserId,
        @Valid UpdateBirthdayRequest updateBirthdayRequest);

    UpdateUserResponse updateUserMessage(String requestUserId,
        @Valid UpdateMessageRequest updateMessageRequest);

    UpdateUserResponse updateUserProfileImage(String requestUserId,
        @Valid UpdateProfileImageUrlRequest updateProfileImageUrlRequest);

    DeleteUserResponse deleteUser(String requestUserId);

    RetrieveUserHomeResponse retrieveUserHome(String requestUserId);

    UserProfileResponse retrieveUserMyPage(String requestUserId);
}
