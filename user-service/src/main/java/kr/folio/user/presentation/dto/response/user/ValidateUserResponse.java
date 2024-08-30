package kr.folio.user.presentation.dto.response.user;

public record ValidateUserResponse(
    boolean isValid,
    String message
) {

}
