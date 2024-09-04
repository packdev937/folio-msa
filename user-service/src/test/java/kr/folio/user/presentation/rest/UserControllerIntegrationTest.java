package kr.folio.user.presentation.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import kr.folio.user.application.ports.input.UserApplicationUseCase;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.request.UpdateBirthdayRequest;
import kr.folio.user.presentation.dto.request.UpdateMessageRequest;
import kr.folio.user.presentation.dto.request.UpdateNicknameRequest;
import kr.folio.user.presentation.dto.request.UpdateProfileImageUrlRequest;
import kr.folio.user.presentation.dto.response.feed.FeedsResponse;
import kr.folio.user.presentation.dto.response.feed.RetrieveFeedResponse;
import kr.folio.user.presentation.dto.response.user.CreateUserResponse;
import kr.folio.user.presentation.dto.response.user.DeleteUserResponse;
import kr.folio.user.presentation.dto.response.user.UpdateUserResponse;
import kr.folio.user.presentation.dto.response.user.UserHomeResponse;
import kr.folio.user.presentation.dto.response.user.UserProfileResponse;
import kr.folio.user.presentation.dto.response.user.ValidateUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@DisplayName("UserController 테스트")
@WebFluxTest(UserController.class)
class UserControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private ObjectMapper objectMapper;

    @MockBean
    private UserApplicationUseCase userApplicationUseCase;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void 유저를_생성한다() throws Exception {
        // Given
        String id = "packdev937";
        String message = "가입이 완료 되었습니다.";
        CreateUserRequest createUserRequest = new CreateUserRequest(id, "admin",
            LocalDate.of(1999, 3, 27));
        CreateUserResponse createUserResponse = new CreateUserResponse(id,
            message);

        when(userApplicationUseCase.createUser(any(CreateUserRequest.class))).thenReturn(
            createUserResponse);

        // When & Then

        EntityExchangeResult<byte[]> result = webTestClient.post().uri("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(createUserRequest))
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$.userId").isEqualTo(id)
            .jsonPath("$.message").isEqualTo(message)
            .returnResult();

        verify(userApplicationUseCase, times(1)).createUser(any(CreateUserRequest.class));
    }

    @Test
    void 아이디를_중복_검증한다() {
        // Given
        String id = "packdev937";
        ValidateUserResponse response = new ValidateUserResponse(true, "사용 가능한 아이디 입니다.");

        when(userApplicationUseCase.validateUserId(id)).thenReturn(response);

        // When & Then
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path("/users/validation/id")
	.queryParam("id", id).build()
            )
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.isValid").isEqualTo(true)
            .jsonPath("$.message").isEqualTo("사용 가능한 아이디 입니다.");

        verify(userApplicationUseCase).validateUserId(id);
    }

    @Test
    void 닉네임을_중복_검증한다() {
        // Given
        String nickname = "admin";
        ValidateUserResponse response = new ValidateUserResponse(true, "사용 가능한 닉네임 입니다.");

        when(userApplicationUseCase.validateUserNickname(nickname)).thenReturn(response);

        // When & Then
        webTestClient.get().uri(uriBuilder -> uriBuilder.path("/users/validation/nickname")
	.queryParam("nickname", nickname).build()
            )
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.isValid").isEqualTo(true)
            .jsonPath("$.message").isEqualTo("사용 가능한 닉네임 입니다.");

        verify(userApplicationUseCase).validateUserNickname(nickname);
    }

    @Test
    void 유저_닉네임_업데이트_한다() {
        // Given
        String userId = "packdev937";
        UpdateNicknameRequest request = new UpdateNicknameRequest("packdev938");
        UpdateUserResponse expectedResponse = new UpdateUserResponse(userId, "회원 정보가 수정되었습니다.");

        when(
            userApplicationUseCase.updateUserNickname(eq(userId), any(UpdateNicknameRequest.class)))
            .thenReturn(expectedResponse);

        // When & Then
        webTestClient.post().uri("/users/nickname")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-USER-ID", userId)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.userId").isEqualTo(userId)
            .jsonPath("$.message").isEqualTo("회원 정보가 수정되었습니다.");

        verify(userApplicationUseCase).updateUserNickname(eq(userId),
            any(UpdateNicknameRequest.class));
    }

    @Test
    void 유저_생일_업데이트_테스트() {
        // Given
        String userId = "packdev937";
        UpdateBirthdayRequest request = new UpdateBirthdayRequest(LocalDate.of(1990, 1, 1));
        UpdateUserResponse expectedResponse = new UpdateUserResponse(userId, "회원 정보가 수정되었습니다.");

        when(
            userApplicationUseCase.updateUserBirthday(eq(userId), any(UpdateBirthdayRequest.class)))
            .thenReturn(expectedResponse);

        // When & Then
        webTestClient.post().uri("/users/birthday")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-USER-ID", userId)  // 헤더로 사용자 ID 설정
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.userId").isEqualTo(userId)
            .jsonPath("$.message").isEqualTo("회원 정보가 수정되었습니다.");

        verify(userApplicationUseCase).updateUserBirthday(eq(userId),
            any(UpdateBirthdayRequest.class));
    }

    @Test
    void 유저_메시지_업데이트_테스트() {
        // Given
        String userId = "packdev937";
        UpdateMessageRequest request = new UpdateMessageRequest("newMessage");
        UpdateUserResponse expectedResponse = new UpdateUserResponse(userId, "회원 정보가 수정되었습니다.");

        when(userApplicationUseCase.updateUserMessage(eq(userId), any(UpdateMessageRequest.class)))
            .thenReturn(expectedResponse);

        // When & Then
        webTestClient.post().uri("/users/message")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-USER-ID", userId)  // 헤더로 사용자 ID 설정
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.userId").isEqualTo(userId)
            .jsonPath("$.message").isEqualTo("회원 정보가 수정되었습니다.");

        verify(userApplicationUseCase).updateUserMessage(eq(userId),
            any(UpdateMessageRequest.class));
    }

    @Test
    void 유저_프로필_이미지_업데이트_테스트() {
        // Given
        String userId = "packdev937";
        UpdateProfileImageUrlRequest request = new UpdateProfileImageUrlRequest(
            "https://packdev937.s3.ap-northeast-2.amazonaws.com/profile/2021/07/01/1.jpg");
        UpdateUserResponse expectedResponse = new UpdateUserResponse(userId, "회원 정보가 수정되었습니다.");

        when(userApplicationUseCase.updateUserProfileImage(eq(userId),
            any(UpdateProfileImageUrlRequest.class)))
            .thenReturn(expectedResponse);

        // When & Then
        webTestClient.post().uri("/users/profile")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-USER-ID", userId)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.userId").isEqualTo(userId)
            .jsonPath("$.message").isEqualTo("회원 정보가 수정되었습니다.");

        verify(userApplicationUseCase).updateUserProfileImage(eq(userId),
            any(UpdateProfileImageUrlRequest.class));
    }

    @Test
    void 유저_삭제_테스트() {
        // Given
        String userId = "packdev937";
        DeleteUserResponse expectedResponse = new DeleteUserResponse(userId, "회원 탈퇴 처리가 되었습니다.");

        when(userApplicationUseCase.deleteUser(userId)).thenReturn(expectedResponse);

        // When & Then
        webTestClient.delete().uri("/users")
            .header("X-USER-ID", userId)  // 헤더로 사용자 ID 설정
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.userId").isEqualTo(userId)
            .jsonPath("$.message").isEqualTo("회원 탈퇴 처리가 되었습니다.");

        verify(userApplicationUseCase).deleteUser(userId);
    }

    @Test
    void 유저_홈_조회_테스트() {
        // Given
        User user = User.builder()
            .id("packdev937")
            .nickname("admin")
            .profileImageUrl("https://packdev937.s3.ap-northeast-2.amazonaws.com/profile/2021/07/01/1.jpg")
            .build();

        UserHomeResponse response = UserHomeResponse.builder()
            .userProfile(UserProfileResponse.of(user))
            .feeds(FeedsResponse.from(List.of(
                new RetrieveFeedResponse(1L, "photoImageUrl1", LocalDateTime.of(2021, 1, 1, 0, 0)),
                new RetrieveFeedResponse(2L, "photoImageUrl2", LocalDateTime.of(2021, 1, 1, 0, 0)),
                new RetrieveFeedResponse(3L, "photoImageUrl3", LocalDateTime.of(2021, 1, 1, 0, 0))
            )))
            .build();

        when(userApplicationUseCase.retrieveUserHome(user.getId())).thenReturn(response);

        // When & Then
        webTestClient.get().uri("/users/home")
            .header("X-USER-ID", user.getId())  // 헤더로 사용자 ID 설정
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.userProfile.id").isEqualTo(user.getId()) // 유저 프로필 ID 확인
            .jsonPath("$.userProfile.nickname").isEqualTo(user.getNickname()) // 유저 닉네임 확인
            .jsonPath("$.userProfile.profileImageUrl").isEqualTo(user.getProfileImageUrl()) // 유저 프로필 이미지 확인
            .jsonPath("$.feeds.feeds[0].photoImageUrl").isEqualTo("photoImageUrl1") // 첫 번째 피드 이미지 URL 확인
            .jsonPath("$.feeds.feeds[1].photoImageUrl").isEqualTo("photoImageUrl2") // 두 번째 피드 이미지 URL 확인
            .jsonPath("$.feeds.feeds[2].photoImageUrl").isEqualTo("photoImageUrl3"); // 세 번째 피드 이미지 URL 확인

        verify(userApplicationUseCase).retrieveUserHome(user.getId());
    }


    @Test
    void 유저_마이페이지_조회_테스트() {
        // Given
        User user = User.builder()
            .id("packdev937")
            .nickname("admin")
            .birthday(LocalDate.of(1999, 3, 27))
            .build();

        UserProfileResponse response = UserProfileResponse.of(user);

        when(userApplicationUseCase.retrieveUserMyPage(user.getId())).thenReturn(response);

        // When & Then
        webTestClient.get().uri("/users/mypage")
            .header("X-USER-ID", user.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(user.getId())
            .jsonPath("$.nickname").isEqualTo(user.getNickname())
            .jsonPath("$.profileImageUrl").isEqualTo(user.getProfileImageUrl());

        verify(userApplicationUseCase).retrieveUserMyPage(user.getId());
    }
}