package kr.folio.feed.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.folio.feed.domain.core.entity.Feed.TaggedUser;

/**
 * CreateFeedRequest
 *
 * @param userId  피드를 추가 할 유저 아이디 (유저 정보)
 * @param photoId 피드에 추가 할 포토 아이디 (사진 정보)
 */
public record CreateFeedRequest(
    @NotNull
    String userId,
    @NotNull
    Long photoId,
    @NotNull
    List<TaggedUser> taggedUsers
    // todo : 다른 서비스에서 TaggedUser 도메인을 만들자
) {

}
