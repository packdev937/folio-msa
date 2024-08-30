package kr.folio.photo.presentation.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.folio.photo.infrastructure.annotation.CurrentUserId;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.request.UpdatePhotoImageRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import kr.folio.photo.presentation.dto.response.DeletePhotoResponse;
import kr.folio.photo.presentation.dto.response.RetrievePhotoResponse;
import kr.folio.photo.presentation.dto.response.TrendResponse;
import kr.folio.photo.presentation.dto.response.UpdatePhotoResponse;
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
public interface PhotoControllerDocs {

    @Operation(summary = "포토 생성", description = "포토를 생성합니다.")
    @PostMapping
    ResponseEntity<CreatePhotoResponse> createPhoto(
        @RequestBody CreatePhotoRequest createPhotoRequest
    );

    @Operation(summary = "포토 상세 조회", description = "포토를 상제 조회합니다. Feed-Service에 의해 호출됩니다.")
    @GetMapping("/{photoId}")
    ResponseEntity<RetrievePhotoResponse> retrievePhoto(
        @PathVariable(name = "photoId") Long photoId
    );

    @Operation(summary = "포토 이미지 수정", description = "포토 이미지를 수정합니다. Feed-Service에 의해 호출됩니다.")
    @PatchMapping
    ResponseEntity<UpdatePhotoResponse> updatePhotoImage(
        @CurrentUserId String requestUserId,
        @RequestBody UpdatePhotoImageRequest updatePhotoImageRequest
    );

    @Operation(summary = "포토 피드 삭제", description = "포토를 삭제합니다. 피드가 하나라도 없다면 삭제됩니다.")
    @DeleteMapping("/{photoId}")
    ResponseEntity<DeletePhotoResponse> deletePhoto(
        @PathVariable(name = "photoId") Long photoId
    );

    // 트렌드 조회
    @Operation(summary = "트렌드 조회", description = "트렌드를 조회합니다.")
    @GetMapping("/trend")
    ResponseEntity<TrendResponse> retrieveRecommendationPhoto(
        @CurrentUserId String requestUserId
    );
}
