package kr.folio.photo.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.folio.photo.domain.core.vo.AccessRange;


public record CreatePhotoRequest(
    @NotNull
    Long photoId,
    @NotNull
    AccessRange accessRange,
    @NotNull
    List<String> userIds
) {

}
