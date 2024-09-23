package kr.folio.user.application.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import kr.folio.user.application.handler.UserApplicationHandler;
import kr.folio.user.application.mapper.UserDataMapper;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.service.UserDomainService;
import kr.folio.user.domain.service.UserDomainUseCase;
import kr.folio.user.infrastructure.exception.UserAlreadyExistsException;
import kr.folio.user.infrastructure.exception.UserNotFoundException;
import kr.folio.user.persistence.repository.FakeUserRepository;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.request.UpdateBirthdayRequest;
import kr.folio.user.presentation.dto.request.UpdateMessageRequest;
import kr.folio.user.presentation.dto.request.UpdateNicknameRequest;
import kr.folio.user.presentation.dto.request.UpdateProfileImageUrlRequest;
import kr.folio.user.presentation.dto.response.user.CreateUserResponse;
import kr.folio.user.presentation.dto.response.user.DeleteUserResponse;
import kr.folio.user.presentation.dto.response.user.UpdateUserResponse;
import kr.folio.user.presentation.dto.response.user.ValidateUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserApplicationHandler 테스트")
class UserApplicationHandlerTest {

    private UserDomainUseCase userDomainService;
    private UserRepository userRepository;
    private UserApplicationHandler userApplicationHandler;
    private UserEventService userEventService;

    @BeforeEach
    void setUp() {
        userDomainService = new UserDomainService();
        userRepository = new FakeUserRepository();
        userEventService = new UserEventService(null);
        userApplicationHandler = new UserApplicationHandler(userDomainService, userRepository,
            null, null, userEventService, null);
    }

    @Test
    void 유저를_생성한다() {
        // Given
        CreateUserRequest createUserRequest = new CreateUserRequest("packdev937", "admin",
            LocalDate.of(1999, 3, 27));

        // When
        CreateUserResponse response = userApplicationHandler.createUser(createUserRequest);

        // Then
        assertNotNull(response);
        assertEquals("가입이 완료 되었습니다.", response.message());
        assertEquals(createUserRequest.id(), response.userId());

        User savedUser = userRepository.findUserById(response.userId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals(createUserRequest.id(), savedUser.getId());
    }

    @Test
    void 유저_닉네임을_변경한다() {
        // Given
        String userId = "packdev937";
        userRepository.saveUser(
            new User(userId, "admin", "message", "profileUrl", LocalDate.of(1999, 3, 27)));
        String newNickname = "root";

        // When
        UpdateUserResponse response = userApplicationHandler.updateUserNickname(userId,
            new UpdateNicknameRequest(newNickname));

        // Then
        assertNotNull(response);
        assertEquals("회원 정보가 수정되었습니다.", response.message());
        assertEquals(userId, response.userId());

        User updatedUser = userRepository.findUserById(userId)
            .orElseThrow(() -> new UserNotFoundException());
        assertEquals(newNickname, updatedUser.getNickname());
    }

    @Test
    void 유저_메세지를_변경한다() {
        // Given
        String userId = "packdev937";
        userRepository.saveUser(
            new User(userId, "admin", "message", "profileUrl", LocalDate.of(1999, 3, 27)));
        String newMessage = "Hello World!";

        // When
        UpdateUserResponse response = userApplicationHandler.updateUserMessage(userId,
            new UpdateMessageRequest(newMessage));

        // Then
        assertNotNull(response);
        assertEquals("회원 정보가 수정되었습니다.", response.message());
        assertEquals(userId, response.userId());

        User updatedUser = userRepository.findUserById(userId)
            .orElseThrow(() -> new UserNotFoundException());
        assertEquals(newMessage, updatedUser.getMessage());
    }

    @Test
    void 유저_프로필_사진을_변경한다() {
        // Given
        String userId = "packdev937";
        userRepository.saveUser(
            new User(userId, "admin", "message", "profileUrl", LocalDate.of(1999, 3, 27)));
        String newProfileUrl = "default!";

        // When
        UpdateUserResponse response = userApplicationHandler.updateUserProfileImage(userId,
            new UpdateProfileImageUrlRequest(newProfileUrl));

        // Then
        assertNotNull(response);
        assertEquals("회원 정보가 수정되었습니다.", response.message());
        assertEquals(userId, response.userId());

        User updatedUser = userRepository.findUserById(userId)
            .orElseThrow(() -> new UserNotFoundException());
        assertEquals(newProfileUrl, updatedUser.getProfileImageUrl());
    }

    @Test
    void 유저_생일을_변경한다() {
        // Given
        String userId = "packdev937";
        userRepository.saveUser(
            new User(userId, "admin", "message", "profileUrl", LocalDate.of(1999, 3, 27)));
        LocalDate newBirthday = LocalDate.of(1999, 3, 28);

        // When
        UpdateUserResponse response = userApplicationHandler.updateUserBirthday(userId,
            new UpdateBirthdayRequest(newBirthday));

        // Then
        assertNotNull(response);
        assertEquals("회원 정보가 수정되었습니다.", response.message());
        assertEquals(userId, response.userId());

        User updatedUser = userRepository.findUserById(userId)
            .orElseThrow(() -> new UserNotFoundException());
        assertEquals(newBirthday, updatedUser.getBirthday());
    }

    @Test
    void 유저를_삭제한다() {
        // Given
        String userId = "packdev937";
        User user = new User(userId, "admin", "message", "profileUrl", LocalDate.of(1999, 3, 27));
        userRepository.saveUser(user);

        // When
        DeleteUserResponse response = userApplicationHandler.deleteUser(userId);

        // Then
        assertNotNull(response);
        assertEquals("회원 탈퇴 처리가 되었습니다.", response.message());

        // 삭제된 유저가 데이터베이스에 없는지 확인
        assertFalse(userRepository.findUserById(userId).isPresent());
    }

    @Test
    void 중복된_아이디를_검증한다() {
        // Given
        User user = User.builder().id("packdev937").nickname("admin").build();

        userRepository.saveUser(user);
        String duplicatedId = "packdev937";
        String notDuplicatedId = "packdev938";

        // When & then
        assertThrows(UserAlreadyExistsException.class, () -> {
            userApplicationHandler.validateDuplicatedId(duplicatedId);
        });

        ValidateUserResponse response = userApplicationHandler.validateDuplicatedId(
            notDuplicatedId);
        assertNotNull(response);
        assertEquals("사용 가능한 아이디 입니다.", response.message());
    }

    @Test
    void 중복된_닉네임을_검증한다() {
        // Given
        User user = User.builder().id("packdev937").nickname("admin").build();

        userRepository.saveUser(user);
        String duplicatedNickname = "admin";
        String notDuplicatedNickname = "root";

        // When & then
        assertThrows(UserAlreadyExistsException.class, () -> {
            userApplicationHandler.validateDuplicatedNickname(duplicatedNickname);
        });

        ValidateUserResponse response = userApplicationHandler.validateDuplicatedNickname(
            notDuplicatedNickname);
        assertNotNull(response);
        assertEquals("사용 가능한 닉네임 입니다.", response.message());
    }
}
