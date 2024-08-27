package kr.folio.user.application.service;

import kr.folio.user.application.handler.UserApplicationHandler;
import kr.folio.user.application.mapper.UserDataMapper;
import kr.folio.user.application.ports.input.UserApplicationUseCase;
import kr.folio.user.application.ports.output.UserMessagePublisher;
import kr.folio.user.domain.core.event.UserCreatedEvent;
import kr.folio.user.infrastructure.annotation.ApplicationService;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
            .toResponse(userCreatedEvent.user(), "User saved successfully!");
    }
}