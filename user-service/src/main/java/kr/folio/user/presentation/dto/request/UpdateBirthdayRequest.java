package kr.folio.user.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "생일 변경 요청")
public record UpdateBirthdayRequest(
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday
) {

}
