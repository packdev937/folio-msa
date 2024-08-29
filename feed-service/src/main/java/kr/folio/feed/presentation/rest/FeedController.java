package kr.folio.feed.presentation.rest;

import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.DeletePhotoResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedResponse;
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
    public String healthCheck() {
        return "Welcome to feed service";
    }

    @Override
    public ResponseEntity<CreateFeedResponse> createFeed(CreateFeedRequest createPhotoRequest) {
        return new ResponseEntity<>(
            feedApplicationUseCase.createFeed(createPhotoRequest)
            , HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<RetrieveFeedResponse> retrieveFeed(String requestUserId, Long feedId) {
        return new ResponseEntity<>(
            feedApplicationUseCase.retrieveFeed(requestUserId, feedId)
            , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<UpdateFeedResponse> updateFeedAccessRange(String requestUserId,
        UpdateFeedAccessRangeRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<DeletePhotoResponse> deletePhoto(Long photoId) {
        return null;
    }

    @Override
    public ResponseEntity<FeedsResponse> retrieveRecommendationPhoto(String requestUserId) {
        return null;
    }

    @Override
    public ResponseEntity<FeedsResponse> retrieveUserFeeds(String userId) {
        return null;
    }

    // 피드 생성 -> 포토 서비스에 해당 photoId로 되어 있는 정보로 요청하면 그거에 맞춰 생성

    // 피드 상세 조회 -> 포토 서비스에 해당 photoId로 되어 있는 정보를 불러옴

    // 피드 삭제 -> 해당 피드를 삭제함

    // 피드 업데이트 -> AccessRange에 따라 수정 가능한 정보가 다름

    // 피드 전체 조회 -> 해당 유저에 대한 피드 전체 조회 요청이 들어오면 요청해줌
}
