package kr.folio.user.presentation.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "유저 API", description = "유저 정보를 다루는 API")
@Validated
@RequestMapping("/users")
public interface UserControllerDocs {

    @Operation(summary = "유저 생성", description = "유저를 생성합니다.")
    @PostMapping
    ResponseEntity<CreateUserResponse> createUser(
        @RequestBody CreateUserRequest request);

}