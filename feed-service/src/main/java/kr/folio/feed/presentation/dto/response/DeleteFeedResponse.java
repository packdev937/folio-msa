package kr.folio.feed.presentation.dto.response;

/**
 * @param feedId  삭제 하는 FeedId
 * @param message 삭제 성공 메세지
 */
public record DeleteFeedResponse(
    Long feedId,
    String message
) {

}
