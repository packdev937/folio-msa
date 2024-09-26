package kr.folio.user.presentation.rest;

import java.util.HashMap;
import java.util.Map;
import kr.folio.user.application.ports.input.UserApplicationUseCase;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserControllerDocs {

    private final UserApplicationUseCase userApplicationUseCase;

    @GetMapping("/health_check")
    public Mono<Map<String, Object>> healthCheck() {
        Map<String, Object> healthDetails = new HashMap<>();
        healthDetails.put("status", "User-Service가 실행 중 입니다.");
        healthDetails.put("serverTime", System.currentTimeMillis());
        healthDetails.put("freeMemory", Runtime.getRuntime().freeMemory());

        return Mono.just(healthDetails);
    }

    @Override
    public Mono<ResponseEntity<CreateUserResponse>> createUser(CreateUserRequest request) {
        log.info("Creating user with id : {} nickname : {}", request.id(), request.nickname());
        return Mono.just(
            new ResponseEntity<>(userApplicationUseCase.createUser(request), HttpStatus.CREATED)
        );
    }

    @Override
    public Mono<ResponseEntity<ValidateUserResponse>> validateUserId(String id) {
        log.info("Validating duplicated user. ID : {}", id);
        return Mono.just(
            new ResponseEntity<>(userApplicationUseCase.validateUserId(id), HttpStatus.OK)
        );
    }

    @Override
    public Mono<ResponseEntity<ValidateUserResponse>> validateUserNickname(String nickname) {
        log.info("Validating duplicated user nickname. Nickname : {}", nickname);
        return Mono.just(
            new ResponseEntity<>(userApplicationUseCase.validateUserNickname(nickname), HttpStatus.OK)
        );
    }

    @Override
    public Mono<ResponseEntity<UpdateUserResponse>> updateUserNickname(String requestUserId,
        UpdateNicknameRequest updateNicknameRequest) {
        log.info("Updating user nickname. User ID : {} Updating Nickname : {} ", requestUserId,
            updateNicknameRequest.nickname());
        return Mono.just(
            new ResponseEntity<>(
                userApplicationUseCase.updateUserNickname(requestUserId, updateNicknameRequest),
                HttpStatus.OK
            )
        );
    }

    @Override
    public Mono<ResponseEntity<UpdateUserResponse>> updateUserBirthday(String requestUserId,
        UpdateBirthdayRequest updateBirthdayRequest) {
        log.info("Updating user birthday. User ID : {} Updating Birthday {}", requestUserId,
            updateBirthdayRequest.birthday());
        return Mono.just(
            new ResponseEntity<>(
                userApplicationUseCase.updateUserBirthday(requestUserId, updateBirthdayRequest),
                HttpStatus.OK
            )
        );
    }

    @Override
    public Mono<ResponseEntity<UpdateUserResponse>> updateUserMessage(String requestUserId,
        UpdateMessageRequest updateMessageRequest) {
        log.info("Updating user message. User ID : {} Updating Message : {} ",
            requestUserId, updateMessageRequest.message());
        return Mono.just(
            new ResponseEntity<>(
                userApplicationUseCase.updateUserMessage(requestUserId, updateMessageRequest),
                HttpStatus.OK
            )
        );
    }

    @Override
    public Mono<ResponseEntity<UpdateUserResponse>> updateUserProfileImage(String requestUserId,
        UpdateProfileImageUrlRequest updateProfileImageUrlRequest) {
        log.info("Updating user profile. User ID : {} Updating ImageUrl : {} ",
            requestUserId, updateProfileImageUrlRequest.profileImageFile());
        return Mono.just(
            new ResponseEntity<>(
                userApplicationUseCase.updateUserProfileImage(requestUserId, updateProfileImageUrlRequest),
                HttpStatus.OK
            )
        );
    }

    @Override
    public Mono<ResponseEntity<DeleteUserResponse>> deleteUser(String requestUserId) {
        log.info("Deleting user with id : {}", requestUserId);
        return Mono.just(
            new ResponseEntity<>(
                userApplicationUseCase.deleteUser(requestUserId), HttpStatus.OK
            )
        );
    }

    @Override
    public Mono<ResponseEntity<UserHomeResponse>> retrieveUserHome(String requestUserId) {
        log.info("Retrieving user home with id : {}", requestUserId);
        return Mono.just(
            new ResponseEntity<>(
                userApplicationUseCase.retrieveUserHome(requestUserId), HttpStatus.OK
            )
        );
    }

    @Override
    public Mono<ResponseEntity<UserProfileResponse>> retrieveUserMyPage(String requestUserId) {
        log.info("Retrieving user my page with id : {}", requestUserId);
        return Mono.just(
            new ResponseEntity<>(
                userApplicationUseCase.retrieveUserMyPage(requestUserId), HttpStatus.OK
            )
        );
    }
}
