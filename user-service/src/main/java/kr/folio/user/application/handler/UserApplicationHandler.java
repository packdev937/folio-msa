package kr.folio.user.application.handler;

import java.util.function.Consumer;
import kr.folio.user.application.mapper.UserDataMapper;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.event.UserCreatedEvent;
import kr.folio.user.domain.service.UserDomainUseCase;
import kr.folio.user.infrastructure.exception.UserAlreadyExistsException;
import kr.folio.user.infrastructure.exception.UserNotFoundException;
import kr.folio.user.presentation.dto.request.*;
import kr.folio.user.presentation.dto.response.DeleteUserResponse;
import kr.folio.user.presentation.dto.response.UpdateUserResponse;
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
        UserCreatedEvent userCreatedEvent = userDomainUseCase.validateUser(
            user);
        User savedUser = userRepository.createUser(user);

        if (savedUser == null) {
            log.error("Could not save user with id: {}", createUserRequest.id());
            throw new UserNotFoundException();
        }

        log.info("Returning UserCreatedEvent for user id: {}", createUserRequest.id());
        return userCreatedEvent;
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

    public UpdateUserResponse updateUserNickname(String id, UpdateNicknameRequest request) {
        return updateUserField(id, user -> user.updateNickname(request.nickname()));
    }

    public UpdateUserResponse updateUserBirthday(String id, UpdateBirthdayRequest request) {
        return updateUserField(id, user -> user.updateBirthday(request.birthday()));
    }

    public UpdateUserResponse updateUserMessage(String id, UpdateMessageRequest request) {
        return updateUserField(id, user -> user.updateMessage(request.message()));
    }

    public UpdateUserResponse updateUserProfileImage(String id,
        UpdateProfileImageUrlRequest request) {
        return updateUserField(id, user -> user.updateProfileImageUrl(request.profileImageUrl()));
    }

    public DeleteUserResponse deleteUser(String id) {
        User user = findUserByIdOrThrow(id);
        userRepository.deleteUser(user);
        return new DeleteUserResponse(id, "User deleted successfully!");
    }

    private User findUserByIdOrThrow(String id) {
        return userRepository.findUserById(id).orElseThrow(UserNotFoundException::new);
    }

    private UpdateUserResponse updateUserField(String id, Consumer<User> updateFunction) {
        User user = findUserByIdOrThrow(id);
        updateFunction.accept(user);
        User updatedUser = userRepository.updateUser(user);

        if (updatedUser == null) {
            log.error("Could not update user with id: {}", id);
            throw new UserNotFoundException();
        }

        return userDataMapper.toUpdateResponse(updatedUser, "User updated successfully!");
    }
}
