package kr.folio.feed.presentation.dto.request;

public record UpdateFeedAccessRangeRequest(
    Long feedId,
    String updatedAccessRange
) {

}
