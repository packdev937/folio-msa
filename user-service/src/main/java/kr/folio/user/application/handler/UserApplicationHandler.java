package kr.folio.user.application.handler;

import java.util.function.Consumer;
import kr.folio.user.application.mapper.UserDataMapper;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.event.CreatedUserEvent;
import kr.folio.user.domain.service.UserDomainUseCase;
import kr.folio.user.infrastructure.client.FeedServiceClient;
import kr.folio.user.infrastructure.client.PhotoServiceClient;
import kr.folio.user.infrastructure.exception.UserAlreadyExistsException;
import kr.folio.user.infrastructure.exception.UserNotFoundException;
import kr.folio.user.presentation.dto.request.*;
import kr.folio.user.presentation.dto.response.DeleteUserResponse;
import kr.folio.user.presentation.dto.response.FeedsResponse;
import kr.folio.user.presentation.dto.response.RetrieveUserHomeResponse;
import kr.folio.user.presentation.dto.response.UpdateUserResponse;
import kr.folio.user.presentation.dto.response.UserProfileResponse;
import kr.folio.user.presentation.dto.response.ValidateUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserApplicationHandler {

    private final UserDomainUseCase userDomainUseCase;
    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;
    private final PhotoServiceClient photoServiceClient;
    private final FeedServiceClient feedServiceClient;

    @Transactional
    public CreatedUserEvent createUser(CreateUserRequest createUserRequest) {
        User user = userDataMapper.toDomain(createUserRequest);
        CreatedUserEvent userCreatedEvent = userDomainUseCase.validateUser(
            user);
        User savedUser = userRepository.createUser(user);

        if (savedUser == null) {
            log.error("Could not save user with id: {} in UserApplicationHandler.java",
	createUserRequest.id());
            throw new UserNotFoundException();
        }

        log.info("Returning CreatedUserEvent for user id: {}", createUserRequest.id());
        return userCreatedEvent;
    }

    public RetrieveUserHomeResponse retrieveUserHome(String requestUserId) {
        UserProfileResponse userProfileResponse = retrieveUserProfile(requestUserId);
        FeedsResponse feeds = feedServiceClient.retrieveFeeds(requestUserId);

        log.info("Returning RetrieveUserHomeResponse for user id: {}", requestUserId);
        return userDataMapper.toRetrieveUserHomeResponse(userProfileResponse, feeds);
    }

    public ValidateUserResponse validateDuplicatedId(String id) {
        userRepository.findUserById(id).ifPresent(user -> {
            throw new UserAlreadyExistsException();
        });

        return new ValidateUserResponse(true, "사용 가능한 아이디 입니다.");
    }

    public ValidateUserResponse validateDuplicatedNickname(String nickname) {
        userRepository.findUserByNickname(nickname).ifPresent(user -> {
            throw new UserAlreadyExistsException();
        });

        return new ValidateUserResponse(true, "사용 가능한 닉네임 입니다.");
    }

    public UpdateUserResponse updateUserNickname(String requestUserId,
        UpdateNicknameRequest request) {
        return updateUserField(requestUserId, user -> user.updateNickname(request.nickname()));
    }

    public UpdateUserResponse updateUserBirthday(String requestUserId,
        UpdateBirthdayRequest request) {
        return updateUserField(requestUserId, user -> user.updateBirthday(request.birthday()));
    }

    public UpdateUserResponse updateUserMessage(String requestUserId,
        UpdateMessageRequest request) {
        return updateUserField(requestUserId, user -> user.updateMessage(request.message()));
    }

    public UpdateUserResponse updateUserProfileImage(String requestUserId,
        UpdateProfileImageUrlRequest request) {
        return updateUserField(requestUserId,
            user -> user.updateProfileImageUrl(request.profileImageUrl()));
    }

    public DeleteUserResponse deleteUser(String requestUserId) {
        User user = findUserByIdOrThrow(requestUserId);
        userRepository.deleteUser(user);
        return new DeleteUserResponse(requestUserId, "회원 탈퇴 처리가 되었습니다.");
    }

    private User findUserByIdOrThrow(String id) {
        return userRepository.findUserById(id).orElseThrow(UserNotFoundException::new);
    }

    private UpdateUserResponse updateUserField(String id, Consumer<User> updateFunction) {
        User user = findUserByIdOrThrow(id);
        updateFunction.accept(user);
        User updatedUser = userRepository.updateUser(user);

        if (updatedUser == null) {
            log.error("Could not update user with id: {} in UserApplicationHandler.java", id);
            throw new UserNotFoundException();
        }

        return userDataMapper.toUpdateResponse(updatedUser, "회원 정보가 수정되었습니다.");
    }

    public UserProfileResponse retrieveUserProfile(String requestUserId) {
        User user = findUserByIdOrThrow(requestUserId);

        return userDataMapper.toUserProfileResponse(user);
    }
}