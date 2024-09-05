package kr.folio.feed.presentation.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.folio.feed.infrastructure.annotation.CurrentUserId;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.DeleteFeedResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedDetailResponse;
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

@Tag(name = "피드 API", description = "피드 관련 기능에 대한 API")
@Validated
@RequestMapping("/feeds")
public interface FeedControllerDocs {

    @Operation(summary = "피드 생성", description = "CreatePhotoEvent 수신 후 호출 됩니다.")
    @PostMapping
    ResponseEntity<CreateFeedResponse> createFeed(
        @Valid @RequestBody CreateFeedRequest createFeedRequest
    );

    @Operation(summary = "사용자 전체 피드 조회", description = "사용자의 전체 피드를 조회 합니다.")
    @GetMapping("/{userId}")
    ResponseEntity<FeedsResponse> retrieveFeeds(
        @CurrentUserId String requestUserId,
        @PathVariable(name = "userId") String targetUserId
    );

    @Operation(summary = "사용자 피드 상세 조회", description = "사용자의 피드를 상세 조회합니다. 요청한 유저에 따라 반환 되는 정보가 다를 수 있습니다.")
    @GetMapping("/detail/{feedId}")
    ResponseEntity<RetrieveFeedDetailResponse> retrieveFeedDetail(
        @CurrentUserId String requestUserId,
        @PathVariable(name = "feedId") Long feedId
    );

    @Operation(summary = "피드 접근 권한 수정", description = "피드의 접근 권한을 수정합니다.")
    @PatchMapping
    ResponseEntity<UpdateFeedResponse> updateFeedAccessRange(
        @CurrentUserId String requestUserId,
        @Valid @RequestBody UpdateFeedAccessRangeRequest updateFeedAccessRangeRequest
    );

    @Operation(summary = "피드를 삭제 합니다", description = "피드 삭제 권한 확인 후 피드를 삭제합니다. 만약 마지막 피드라면 Photo-Service에 포토 삭제 요청을 진행합니다. ")
    @DeleteMapping("/{feedId}")
    ResponseEntity<DeleteFeedResponse> deleteFeed(
        @PathVariable(name = "feedId") Long feedId
    );
}
