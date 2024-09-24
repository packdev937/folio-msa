package kr.folio.user.infrastructure.client;

import kr.folio.user.infrastructure.annotation.CurrentUserId;
import kr.folio.user.presentation.dto.response.feed.FeedsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/*
    name : Eureka 서버에 등록된 이름
    path : 공통 URI
 */
@FeignClient(name = "feed-service", path = "/feeds")
public interface FeedServiceClient {

    @GetMapping
    FeedsResponse retrieveFeeds(@CurrentUserId String requestUserId);

}

