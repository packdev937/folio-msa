package kr.folio.photo.presentation.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.folio.photo.infrastructure.annotation.CurrentUserId;
import kr.folio.photo.persistence.entity.PhotoEntity;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "포토 API", description = "포토 정보를 다루는 API")
@Validated
@RequestMapping("/v1/photos")
public interface PhotoControllerDocs {

    @Operation(summary = "포토 피드 생성", description = "포토를 생성합니다.")
    @PostMapping
    ResponseEntity<CreatePhotoResponse> createPhoto(
        @CurrentUserId String id,
        @RequestBody CreatePhotoRequest request
    );
}
