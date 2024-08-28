package kr.folio.photo.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import kr.folio.photo.domain.core.vo.BrandType;

public record CreatePhotoRequest(
    @NotNull
    Long id,
    @NotNull
    BrandType brandType,
    @NotNull
    String photoUrl,
    @NotNull
    ArrayList<String> userIds
) {

}
