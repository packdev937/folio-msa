package kr.folio.photo.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import kr.folio.photo.domain.core.vo.BrandType;

@Schema(description = "포토 상세 조회 응답")
public record RetrievePhotoResponse(
    Long photoId,
    String photoImageUrl,
    BrandType brandType,
    List<String> taggedUsers,
    LocalDateTime updatedAt
) {

}
