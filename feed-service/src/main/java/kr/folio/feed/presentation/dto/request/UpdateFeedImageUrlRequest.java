package kr.folio.feed.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record UpdateFeedImageUrlRequest(
    @NotNull
    Long feedId,
    @URL
    @NotNull
    String updatedImageUri
) {

}
