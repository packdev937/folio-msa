package kr.folio.feed.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.folio.feed.domain.core.vo.AccessRange;
import lombok.Builder;

/**
 * RetrieveFeedDetailResponse
 *
 * @param feedId        피드 아이디
 * @param photoImageUrl 피드에 추가 된 포토 아이디
 */
@Builder
public record RetrieveFeedDetailResponse(
    Long feedId,
    String photoImageUrl,
    AccessRange accessRange,
    List<String> taggedUsers,
    LocalDateTime createdAt
) {

}
