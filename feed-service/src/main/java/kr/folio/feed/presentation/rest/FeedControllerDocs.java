package kr.folio.feed.presentation.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.folio.feed.infrastructure.annotation.CurrentUserId;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.DeletePhotoResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedResponse;
import kr.folio.feed.presentation.dto.response.UpdateFeedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "포토 API", description = "포토 정보를 다루는 API")
@Validated
@RequestMapping("/v1/photos")
public interface FeedControllerDocs {

    @Operation(summary = "피드 생성", description = "Photo-Service와 통신하여 피드를 생성합니다. Photo에 태그된 유저들이 대상입니다.")
    @PostMapping
    ResponseEntity<CreateFeedResponse> createFeed(
        @RequestBody CreateFeedRequest createPhotoRequest
    );

    @Operation(summary = "피드 상세 조회", description = "피드를 상세 조회합니다. 요청한 유저에 따라 반환되는 정보가 다를 수 있습니다.")
    @GetMapping("/{feedId}")
    ResponseEntity<RetrieveFeedResponse> retrieveFeed(
        @CurrentUserId String requestUserId,
        @PathVariable(name = "feedId") Long feedId
    );

    @Operation(summary = "피드 접근 권한 수정", description = "피드의 접근 권한을 수정합니다.")
    @PatchMapping
    ResponseEntity<UpdateFeedResponse> updateFeedAccessRange(
        @CurrentUserId String requestUserId,
        @RequestBody UpdateFeedAccessRangeRequest request
    );

    @Operation(summary = "피드를 삭제 합니다", description = "피드 삭제 권한 확인 후 피드를 삭제합니다. 만약 마지막 피드라면 Photo-Service에 포토 삭제 요청을 진행합니다. ")
    @DeleteMapping("/{photoId}")
    ResponseEntity<DeletePhotoResponse> deletePhoto(
        @PathVariable(name = "photoId") Long photoId
    );

    // 피드 전체 조회
    @Operation(summary = "피드 전체 조회", description = "사용자의 피드를 전체 조회 합니다.")
    @GetMapping
    ResponseEntity<FeedsResponse> retrieveRecommendationPhoto(
        @CurrentUserId String requestUserId
    );

    // 사용자 피드 조회
    @Operation(summary = "사용자 피드 조회", description = "사용자의 피드를 전체 조회 합니다.")
    @GetMapping("/user/{userId}")
    ResponseEntity<FeedsResponse> retrieveUserFeeds(
        @PathVariable(name = "userId") String userId
    );
}
