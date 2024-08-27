package kr.folio.user.presentation.rest;

import kr.folio.user.application.ports.input.UserApplicationUseCase;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import kr.folio.user.presentation.dto.response.ValidateUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserControllerDocs {

    private final UserApplicationUseCase userApplicationUseCase;

    @GetMapping("/health_check")
    public String healthCheck() {
        return "User-Service is working.";
    }

    @Override
    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest request) {
        log.info("Creating user with id : {} nickname : {}", request.id(), request.nickname());
        return new ResponseEntity<>(userApplicationUseCase.createUser(request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ValidateUserResponse> validateUserId(String id) {
        log.info("Validating user id : {}", id);
        return new ResponseEntity<>(
            userApplicationUseCase.validateUserId(id),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<ValidateUserResponse> validateUserNickname(String nickname) {
        log.info("Validating user nickname : {}", nickname);
        return new ResponseEntity<>(
            userApplicationUseCase.validateUserNickname(nickname),
            HttpStatus.OK
        );
    }
}
