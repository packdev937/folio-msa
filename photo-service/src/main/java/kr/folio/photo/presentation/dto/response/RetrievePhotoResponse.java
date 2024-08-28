package kr.folio.photo.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.folio.photo.domain.core.vo.AccessRange;
import kr.folio.photo.domain.core.vo.BrandType;

public record RetrievePhotoResponse(
    Long photoId,
    String photoUrl,
    BrandType brandType,
    AccessRange accessRange,
    LocalDateTime updatedAt,
    List<String> taggedUsers
) {

}
