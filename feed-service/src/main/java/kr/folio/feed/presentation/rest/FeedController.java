package kr.folio.feed.presentation.rest;

import java.util.HashMap;
import java.util.Map;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.DeleteFeedResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedDetailResponse;
import kr.folio.feed.presentation.dto.response.UpdateFeedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/feeds")
public class FeedController implements FeedControllerDocs {

    private final FeedApplicationUseCase feedApplicationUseCase;

    @GetMapping("/health_check")
    public Map<String, Object> healthCheck() {
        Map<String, Object> healthDetails = new HashMap<>();
        healthDetails.put("status", "Feed-Service가 실행 중 입니다.");
        healthDetails.put("serverTime", System.currentTimeMillis());
        healthDetails.put("freeMemory", Runtime.getRuntime().freeMemory());

        return healthDetails;
    }

    /**
     * Photo-Service로 부터 호출 됩니다. 태그 된 유저 (생성자 포함) 모두 자동으로 피드가 생성됩니다.
     *
     * @param createFeedRequest
     * @return CreateFeedResponse
     */
    @Override
    public ResponseEntity<CreateFeedResponse> createFeed(CreateFeedRequest createFeedRequest) {
        log.info("Creating feed with id : {} photoId : {}",
            createFeedRequest.userId(),
            createFeedRequest.photoId()
        );

        return new ResponseEntity<>(
            feedApplicationUseCase.createFeed(createFeedRequest)
            , HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<RetrieveFeedDetailResponse> retrieveFeedDetail(
        String requestUserId,
        Long feedId
    ) {
        log.info("Retrieving feed detail with id : {} by user : {}", feedId, requestUserId);

        return new ResponseEntity<>(
            feedApplicationUseCase.retrieveFeedDetail(requestUserId, feedId)
            , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<UpdateFeedResponse> updateFeedAccessRange(String requestUserId,
        UpdateFeedAccessRangeRequest request) {
        log.info("Updating feed access range with id : {} by user : {}", request.feedId(),
            requestUserId);

        return new ResponseEntity<>(
            feedApplicationUseCase.updateFeedAccessRange(requestUserId, request)
            , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<DeleteFeedResponse> deleteFeed(Long feedId) {
        log.info("Deleting feed with id : {}", feedId);

        return new ResponseEntity<>(
            feedApplicationUseCase.deleteFeed(feedId)
            , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<FeedsResponse> retrieveFeeds(String requestUserId) {
        log.info("Retrieving feeds by user : {}", requestUserId);

        return new ResponseEntity<>(
            feedApplicationUseCase.retrieveFeeds(requestUserId)
            , HttpStatus.OK
        );
    }

    /**
     * 사용자의 피드를 조회합니다. 사용자와의 관계에 따라 반환되는 피드가 다를 수 있습니다.
     *
     * @param requestUserId
     * @param userId
     * @return
     */
    @Override
    public ResponseEntity<FeedsResponse> retrieveUserFeeds(
        String requestUserId,
        String userId) {
        log.info("Retrieving user feeds with id : {} by user : {}", userId, requestUserId);

        return new ResponseEntity<>(
            feedApplicationUseCase.retrieveUserFeeds(requestUserId, userId)
            , HttpStatus.OK
        );
    }
}
