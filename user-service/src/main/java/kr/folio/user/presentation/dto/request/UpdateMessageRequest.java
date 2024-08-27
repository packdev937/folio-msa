package kr.folio.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdateMessageRequest(
    @Length(max = 36)
    @NotNull
    String message
) {

}
