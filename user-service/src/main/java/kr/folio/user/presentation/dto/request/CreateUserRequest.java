package kr.folio.user.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public record CreateUserRequest(
    @NotNull
    @Length(min = 2, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 숫자와 영어로만 이루어져야 합니다.")
    String id,
    @NotNull
    @Length(min = 2, max = 16)
    String nickname,
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday
) {

}
