package kr.folio.user.application.service;

import kr.folio.user.application.handler.UserApplicationHandler;
import kr.folio.user.application.mapper.UserDataMapper;
import kr.folio.user.application.ports.input.UserApplicationUseCase;
import kr.folio.user.application.ports.output.UserMessagePublisher;
import kr.folio.user.domain.core.event.UserCreatedEvent;
import kr.folio.user.infrastructure.annotation.ApplicationService;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.request.UpdateBirthdayRequest;
import kr.folio.user.presentation.dto.request.UpdateMessageRequest;
import kr.folio.user.presentation.dto.request.UpdateNicknameRequest;
import kr.folio.user.presentation.dto.request.UpdateProfileImageUrlRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import kr.folio.user.presentation.dto.response.DeleteUserResponse;
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
        UserCreatedEvent userCreatedEvent = userApplicationHandler.createUser(createUserRequest);
        userMessagePublisher.publish(userCreatedEvent);
        return userDataMapper
            .toUpdateResponse(userCreatedEvent.user(), "User saved successfully!");
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
    public UpdateUserResponse updateUserNickname(String id, UpdateNicknameRequest request) {
        return userApplicationHandler.updateUserNickname(id, request);
    }

    @Override
    public UpdateUserResponse updateUserBirthday(String id, UpdateBirthdayRequest request) {
        return userApplicationHandler.updateUserBirthday(id, request);
    }

    @Override
    public UpdateUserResponse updateUserMessage(String id, UpdateMessageRequest request) {
        return userApplicationHandler.updateUserMessage(id, request);
    }

    @Override
    public UpdateUserResponse updateUserProfileImage(String id, UpdateProfileImageUrlRequest request) {
        return userApplicationHandler.updateUserProfileImage(id, request);
    }

    @Override
    public DeleteUserResponse deleteUser(String id) {
        return userApplicationHandler.deleteUser(id);
    }
}