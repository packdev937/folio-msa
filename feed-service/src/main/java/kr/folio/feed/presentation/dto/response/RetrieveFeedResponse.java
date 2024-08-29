package kr.folio.feed.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.folio.feed.domain.core.entity.AccessRange;
import kr.folio.feed.domain.core.entity.Feed.TaggedUser;
import lombok.Builder;

/**
 * RetrieveFeedResponse
 *
 * @param id          피드 아이디
 * @param userId      피드를 추가 한 유저 아이디
 * @param photoId     피드에 추가 된 포토 아이디
 * @param accessRange 피드 접근 범위
 */
@Builder
public record RetrieveFeedResponse(
    Long id,
    String userId,
    Long photoId,
    AccessRange accessRange,
    List<TaggedUser> taggedUsers,
    LocalDateTime createdAt
    ) {
}
