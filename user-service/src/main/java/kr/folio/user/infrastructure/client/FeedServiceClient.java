package kr.folio.user.infrastructure.client;

import kr.folio.user.infrastructure.annotation.CurrentUserId;
import kr.folio.user.presentation.dto.response.feed.FeedsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "feed-service")
public interface FeedServiceClient {

    @GetMapping("/feeds")
    FeedsResponse retrieveFeeds(
        @CurrentUserId String requestUserId
    );

}

