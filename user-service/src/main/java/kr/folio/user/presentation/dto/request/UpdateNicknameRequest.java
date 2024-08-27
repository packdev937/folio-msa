package kr.folio.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdateNicknameRequest(
    @Length(min = 2, max = 10)
    @NotNull
    String nickname
) {

}
