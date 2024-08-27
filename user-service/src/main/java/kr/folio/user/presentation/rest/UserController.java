package kr.folio.user.presentation.rest;

import kr.folio.user.application.ports.input.UserApplicationUseCase;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
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
}
