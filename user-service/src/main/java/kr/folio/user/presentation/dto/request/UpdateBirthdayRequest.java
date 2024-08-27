package kr.folio.user.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UpdateBirthdayRequest(
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday
) {

}
