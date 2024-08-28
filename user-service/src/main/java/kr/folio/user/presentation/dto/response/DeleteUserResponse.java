package kr.folio.user.presentation.dto.response;

import jakarta.validation.constraints.NotNull;

public record DeleteUserResponse(
    @NotNull
    String userId,
    @NotNull
    String message
) {

}
