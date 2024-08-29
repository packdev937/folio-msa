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
        return new ResponseEntity<>(
            feedApplicationUseCase.updateFeedAccessRange(requestUserId, request)
            , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<DeletePhotoResponse> deletePhoto(Long photoId) {
        return new ResponseEntity<>(
            feedApplicationUseCase.deleteFeed(photoId)
            , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<FeedsResponse> retrieveFeeds(String requestUserId) {
        return new ResponseEntity<>(
            feedApplicationUseCase.retrieveFeeds(requestUserId)
            , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<FeedsResponse> retrieveUserFeeds(
        String requestUserId,
        String userId) {
        return new ResponseEntity<>(
            feedApplicationUseCase.retrieveUserFeeds(requestUserId, userId)
            , HttpStatus.OK
        );
    }
}
