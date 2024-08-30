package kr.folio.user.presentation.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.folio.user.infrastructure.annotation.CurrentUserId;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Tag(name = "유저 API", description = "유저와 관련된 API")
@Validated
@RequestMapping("/users")
public interface UserControllerDocs {

    @Operation(summary = "유저 생성", description = "유저를 생성 합니다.")
    @PostMapping
    ResponseEntity<CreateUserResponse> createUser(
        @RequestBody CreateUserRequest createUserRequest);

    @Operation(summary = "아이디 중복 검증", description = "아이디 중복을 검증 합니다.")
    @GetMapping("/validation/id")
    ResponseEntity<ValidateUserResponse> validateUserId(
        @RequestBody String id);

    @Operation(summary = "닉네임 중복 검증", description = "닉네임 중복을 검증 합니다.")
    @GetMapping("/validation/nickname")
    ResponseEntity<ValidateUserResponse> validateUserNickname(
        @RequestBody String nickname);

    @Operation(summary = "유저 닉네임 변경", description = "유저 닉네임을 변경합니다.")
    @PostMapping("/nickname")
    ResponseEntity<UpdateUserResponse> updateUserNickname(
        @CurrentUserId String requestUserId,
        @RequestBody UpdateNicknameRequest updateNicknameRequest);

    @Operation(summary = "유저 생일 변경", description = "유저 생일을 변경합니다.")
    @PostMapping("/birthday")
    ResponseEntity<UpdateUserResponse> updateUserBirthday(
        @CurrentUserId String requestUserId,
        @RequestBody UpdateBirthdayRequest updateBirthdayRequest);

    @Operation(summary = "유저 메시지 변경", description = "유저 상태 메시지를 변경합니다.")
    @PostMapping("/message")
    ResponseEntity<UpdateUserResponse> updateUserMessage(
        @CurrentUserId String requestUserId,
        @RequestBody UpdateMessageRequest updateMessageRequest);

    @Operation(summary = "유저 프로필 사진 변경", description = "유저 프로필 사진을 변경합니다.")
    @PostMapping("/profile")
    ResponseEntity<UpdateUserResponse> updateUserProfileImage(
        @CurrentUserId String requestUserId,
        @RequestPart UpdateProfileImageUrlRequest updateProfileImageUrlRequest);

    @Operation(summary = "유저 삭제", description = "유저를 삭제합니다.")
    @DeleteMapping
    ResponseEntity<DeleteUserResponse> deleteUser(
        @CurrentUserId String requestUserId);

    @Operation(summary = "유저 홈 조회", description = "유저 홈을 조회합니다.")
    @GetMapping("/home")
    ResponseEntity<RetrieveUserHomeResponse> retrieveUserHome(
        @CurrentUserId String requestUserId);

}