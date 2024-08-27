package kr.folio.user.presentation.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import kr.folio.user.presentation.dto.response.ValidateUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "유저 API", description = "유저 정보를 다루는 API")
@Validated
@RequestMapping("/users")
public interface UserControllerDocs {

    @Operation(summary = "유저 생성", description = "유저를 생성 합니다.")
    @PostMapping
    ResponseEntity<CreateUserResponse> createUser(
        @RequestBody CreateUserRequest request);

    @Operation(summary = "아이디 중복 검증", description = "아이디 중복을 검증 합니다.")
    @GetMapping("/validation/id")
    ResponseEntity<ValidateUserResponse> validateUserId(
        @RequestBody String id);

    @Operation(summary = "닉네임 중복 검증", description = "닉네임 중복을 검증 합니다.")
    @GetMapping("/validation/nickname}")
    ResponseEntity<ValidateUserResponse> validateUserNickname(
        @RequestBody String nickname);
}