package kr.folio.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Schema(description = "유저 메시지 변경 요청")
public record UpdateMessageRequest(
    @Length(max = 36)
    @NotNull
    String message
) {

}
