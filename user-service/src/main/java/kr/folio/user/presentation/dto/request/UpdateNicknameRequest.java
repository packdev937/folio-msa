package kr.folio.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Schema(description = "닉네임 변경 요청")
public record UpdateNicknameRequest(
    @Length(min = 2, max = 10)
    @NotNull
    String nickname
) {

}
