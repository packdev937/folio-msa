package kr.folio.user.presentation.dto.response.user;

import jakarta.validation.constraints.NotNull;

public record CreateUserResponse(
    @NotNull
    String userId,
    @NotNull
    String message
) {

}
