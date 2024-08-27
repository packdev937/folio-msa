package kr.folio.user.presentation.dto.response;

public record ValidateUserResponse(
    boolean isValid,
    String message
) {

}
