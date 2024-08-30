package kr.folio.feed.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateFeedAccessRangeRequest(
    @NotNull
    Long feedId,
    @NotNull
    String updatedAccessRange
) {

}
