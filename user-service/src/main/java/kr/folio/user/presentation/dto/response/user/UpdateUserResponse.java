package kr.folio.user.presentation.dto.response.user;

import jakarta.validation.constraints.NotNull;

public record UpdateUserResponse(
    @NotNull
    String userId,
    @NotNull
    String message
) {

}
