package kr.folio.feed.infrastructure.client;

import kr.folio.feed.domain.core.vo.FollowStatus;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "follow-service")
public interface FollowServiceClient {

    FollowStatus retrieveFollowStatus(String requestUserId, String targetUserId);
}
