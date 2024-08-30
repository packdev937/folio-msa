package kr.folio.user.presentation.dto.response.feed;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * RetrieveFeedResponse
 *
 * @param feedId        피드 아이디
 * @param photoImageUrl 피드에 추가 된 포토 아이디
 */
@Builder
public record RetrieveFeedResponse(
    Long feedId,
    String photoImageUrl,
    LocalDateTime createdAt
) {

}
