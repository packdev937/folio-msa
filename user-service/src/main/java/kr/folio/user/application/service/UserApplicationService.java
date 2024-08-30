package kr.folio.user.application.service;

import kr.folio.user.application.handler.UserApplicationHandler;
import kr.folio.user.application.mapper.UserDataMapper;
import kr.folio.user.application.ports.input.UserApplicationUseCase;
import kr.folio.user.application.ports.output.UserMessagePublisher;
import kr.folio.user.domain.core.event.CreatedUserEvent;
import kr.folio.user.infrastructure.annotation.ApplicationService;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.request.UpdateBirthdayRequest;
import kr.folio.user.presentation.dto.request.UpdateMessageRequest;
import kr.folio.user.presentation.dto.request.UpdateNicknameRequest;
import kr.folio.user.presentation.dto.request.UpdateProfileImageUrlRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import kr.folio.user.presentation.dto.response.DeleteUserResponse;
import kr.folio.user.presentation.dto.response.RetrieveUserHomeResponse;
import kr.folio.user.presentation.dto.response.UpdateUserResponse;
import kr.folio.user.presentation.dto.response.ValidateUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class UserApplicationService implements UserApplicationUseCase {

    private final UserApplicationHandler userApplicationHandler;
    private final UserDataMapper userDataMapper;
    @Qualifier("userCreateEventKafkaPublisher")
    private final UserMessagePublisher userMessagePublisher;

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        CreatedUserEvent createdUserEvent = userApplicationHandler.createUser(createUserRequest);
        userMessagePublisher.publish(createdUserEvent);

        return userDataMapper
            .toCreateResponse(createdUserEvent.user(), "가입이 완료 되었습니다.");
    }

    @Override
    public ValidateUserResponse validateUserId(String id) {
        return userApplicationHandler.validateDuplicatedId(id);
    }

    @Override
    public ValidateUserResponse validateUserNickname(String nickname) {
        return userApplicationHandler.validateDuplicatedNickname(nickname);
    }

    @Override
    public UpdateUserResponse updateUserNickname(String requestUserId,
        UpdateNicknameRequest updateNicknameRequest) {
        return userApplicationHandler.updateUserNickname(requestUserId, updateNicknameRequest);
    }

    @Override
    public UpdateUserResponse updateUserBirthday(String requestUserId,
        UpdateBirthdayRequest updateBirthdayRequest) {
        return userApplicationHandler.updateUserBirthday(requestUserId, updateBirthdayRequest);
    }

    @Override
    public UpdateUserResponse updateUserMessage(String requestUserId,
        UpdateMessageRequest updateMessageRequest) {
        return userApplicationHandler.updateUserMessage(requestUserId, updateMessageRequest);
    }

    @Override
    public UpdateUserResponse updateUserProfileImage(String requestUserId,
        UpdateProfileImageUrlRequest updateProfileImageUrlRequest) {
        return userApplicationHandler.updateUserProfileImage(requestUserId,
            updateProfileImageUrlRequest);
    }

    @Override
    public DeleteUserResponse deleteUser(String requestUserId) {
        return userApplicationHandler.deleteUser(requestUserId);
    }

    @Override
    public RetrieveUserHomeResponse retrieveUserHome(String requestUserId) {
        return userApplicationHandler.retrieveUserHome(requestUserId);
    }
}