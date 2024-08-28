package kr.folio.user.presentation.dto.response;

import jakarta.validation.constraints.NotNull;

public record UpdateUserResponse(
    @NotNull
    String userId,
    @NotNull
    String message
) {

}
