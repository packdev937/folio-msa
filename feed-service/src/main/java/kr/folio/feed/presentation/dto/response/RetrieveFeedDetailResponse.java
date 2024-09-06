package kr.folio.feed.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.folio.feed.domain.core.vo.AccessRange;
import lombok.Builder;

@Builder
public record RetrieveFeedDetailResponse(
    Long feedId,
    String photoImageUrl,
    AccessRange accessRange,
    List<String> taggedUsers,
    LocalDateTime createdAt
) {

}
