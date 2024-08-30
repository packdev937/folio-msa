package kr.folio.user.application.ports.input;

import jakarta.validation.Valid;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.request.UpdateBirthdayRequest;
import kr.folio.user.presentation.dto.request.UpdateMessageRequest;
import kr.folio.user.presentation.dto.request.UpdateNicknameRequest;
import kr.folio.user.presentation.dto.request.UpdateProfileImageUrlRequest;
import kr.folio.user.presentation.dto.response.user.CreateUserResponse;
import kr.folio.user.presentation.dto.response.user.DeleteUserResponse;
import kr.folio.user.presentation.dto.response.user.UserHomeResponse;
import kr.folio.user.presentation.dto.response.user.UpdateUserResponse;
import kr.folio.user.presentation.dto.response.user.UserProfileResponse;
import kr.folio.user.presentation.dto.response.user.ValidateUserResponse;

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

    UserHomeResponse retrieveUserHome(String requestUserId);

    UserProfileResponse retrieveUserMyPage(String requestUserId);
}
