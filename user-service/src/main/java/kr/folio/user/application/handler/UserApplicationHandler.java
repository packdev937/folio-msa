package kr.folio.user.application.handler;

import kr.folio.user.application.mapper.UserDataMapper;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.event.UserCreatedEvent;
import kr.folio.user.domain.core.exception.UserAlreadyExistsException;
import kr.folio.user.domain.core.exception.UserDomainException;
import kr.folio.user.domain.service.UserDomainService;
import kr.folio.user.domain.service.UserDomainUseCase;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
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

    @Transactional
    public UserCreatedEvent createUser(CreateUserRequest createUserRequest) {
        User user = userDataMapper.toDomain(createUserRequest);
        UserCreatedEvent userCreatedEvent = userDomainUseCase.validateUser(user);
        User savedUser = userRepository.createUser(user);

        if (savedUser == null) {
            log.error("Could not save user with id: {}", createUserRequest.id());
            throw new UserDomainException("Could not save user with id " + createUserRequest.id());
        }

        log.info("Returning UserCreatedEvent for user id: {}", createUserRequest.id());
        return userCreatedEvent;
    }

    public ValidateUserResponse validateDuplicatedId(String id) {
        userRepository.findUserById(id).ifPresent(
            user -> {
                throw new UserAlreadyExistsException("아이디", id);
            });

        return new ValidateUserResponse(true, "사용 가능한 아이디 입니다.");
    }

    public ValidateUserResponse validateDuplicatedNickname(String nickname) {
        userRepository.findUserByNickname(nickname).ifPresent(
            user -> {
                throw new UserAlreadyExistsException("닉네임", nickname);
            });
        return new ValidateUserResponse(true, "사용 가능한 닉네임 입니다.");
    }
}
